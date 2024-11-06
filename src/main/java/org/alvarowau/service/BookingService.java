package org.alvarowau.service;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.exception.schedule.AppointmentNotFoundException;
import org.alvarowau.exception.schedule.BookingNotFoundException;
import org.alvarowau.exception.schedule.CustomConcurrencyException;
import org.alvarowau.exception.user.CustomerNotFoundException;
import org.alvarowau.exception.user.InvalidRoleException;
import org.alvarowau.exception.user.StaffNotFoundException;
import org.alvarowau.model.dto.action.ActionLogDTO;
import org.alvarowau.model.dto.booking.*;
import org.alvarowau.model.dto.mapper.MapperBooking;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.Booking;
import org.alvarowau.model.enums.ActionType;
import org.alvarowau.model.enums.BookingStatus;
import org.alvarowau.model.enums.OperationStatus;
import org.alvarowau.repository.BookingRepository;
import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.model.entity.Staff;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.user.service.CustomerService;
import org.alvarowau.user.service.ProviderService;
import org.alvarowau.user.service.StaffService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingNumberGenerator bookingNumberGenerator;
    private final MapperBooking bookingMapper;
    private final AppointmentService appointmentService;
    private final CustomerService customerService;
    private final SecurityContextUtil securityContextUtil;
    private final ActionLogService actionLogService;
    private final StaffService staffService;
    private final ProviderService providerService;

    public BookingResponseCreate createBookingByTrackingNumberAppointment(BookingRequestTrackingNumber bookingRequestTrackingNumber) {
        try {
            Appointment appointment = appointmentService.findByTrackingNumber(bookingRequestTrackingNumber.trackingNumberAppointment())
                    .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with tracking number: "
                            + bookingRequestTrackingNumber.trackingNumberAppointment()));
            Customer customer = getCustomer(bookingRequestTrackingNumber.usernameCustomer());

            return createBooking(customer, appointment);
        } catch (OptimisticLockException e) {
            throw new CustomConcurrencyException("The appointment has already been modified by another transaction.", e);
        }
    }

    public BookingResponseCreate createBookingByIdAppointment(BookingRequestId bookingRequestId) {
        try {
            Appointment appointment = appointmentService.findById(bookingRequestId.id())
                    .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: "
                            + bookingRequestId.id()));
            Customer customer = getCustomer(bookingRequestId.usernameCustomer());


            return createBooking(customer, appointment);
        } catch (OptimisticLockException e) {
            throw new CustomConcurrencyException("The appointment has already been modified by another transaction.", e);
        }
    }

    private Customer getCustomer(String usernameCustomer) {
        return customerService.findByUsername(usernameCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with username: "
                        + usernameCustomer));
    }

    private BookingResponseCreate createBooking(Customer customer, Appointment appointment) {
        Booking booking = Booking.builder()
                .appointment(appointment)
                .customer(customer)
                .status(BookingStatus.CONFIRMED)
                .bookingNumber(bookingNumberGenerator.generateBookingNumber())
                .build();
        appointmentService.markAppointmentAsUnavailable(appointment);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    public Booking findByBookingNumber(String bookingNumber) {
        return bookingRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new BookingNotFoundException("No se encontro el booking por el numero: "
                        + bookingNumber));
    }

    public BookingCancellationResponse cancelBookingByUser(CancelBookingRequest request) {
        return cancelBooking(request.bookingNumber(),
                request.usernameCustomer(),
                request.reason(),
                false);
    }

    public BookingCancellationResponse cancelBookingByStaff(CancelBookingRequest request) {
        if (!securityContextUtil.getUserRole().equals(RoleEnum.STAFF.toString())) {
            throw new InvalidRoleException("User does not have permission to cancel bookings.");
        }
        return cancelBooking(request.bookingNumber(),
                request.usernameCustomer(),
                request.reason(),
                true);
    }

    private BookingCancellationResponse cancelBooking(String bookingNumber, String customerUsername, String reason, boolean isStaff) {
        Booking booking = findByBookingNumber(bookingNumber);
        booking.setStatus(BookingStatus.CANCELED);
        Appointment appointment = booking.getAppointment();
        Customer customer = getCustomer(customerUsername);
        Long userId = null;
        if (isStaff) {
            Staff staff = staffService.findByUsername(securityContextUtil.getAuthenticatedUsername())
                    .orElseThrow(() -> new StaffNotFoundException("Staff not found with username: "));
            userId = staff.getId();
        } else {
            userId = customer.getId();
        }
        ActionLogDTO action = new ActionLogDTO(
                ActionType.CANCEL_BOOKING,
                userId,
                customer.getId(),
                reason
        );
        booking = bookingRepository.save(booking);
        if (booking.getStatus().equals(BookingStatus.CANCELED)) {
            appointment = appointmentService.restoreAppointmentAvailability(appointment);
            if (appointment.isAvailable()) {
                actionLogService.saveAction(action);
                return createResponseCancellation(customerUsername, bookingNumber, reason, OperationStatus.SUCCESS);
            } else {
                log.info("no cambia la apooint");
            }
        } else {
            log.info("no cambia el booking");
        }
        return createResponseCancellation(customerUsername, bookingNumber, reason, OperationStatus.FAILURE);

    }

    private BookingCancellationResponse createResponseCancellation(String customerUsername, String bookingNumber, String reason, OperationStatus status) {
        return new BookingCancellationResponse(
                customerUsername, bookingNumber, reason, status
        );
    }

    public List<BookingHistoryResponse> getBookingHistory() {
        String username = securityContextUtil.getAuthenticatedUsername();
        List<Booking> bookings = bookingRepository.findByCustomerUsername(username);
        return bookings.stream()
                .map(booking -> new BookingHistoryResponse(
                        booking.getBookingNumber(),
                        booking.getAppointment().getDate().toString(),
                        booking.getStatus()))
                .collect(Collectors.toList());
    }

    public List<BookingHistoryResponse> getBookingHistory(LocalDate startDate, LocalDate endDate) {
        String username = securityContextUtil.getAuthenticatedUsername();
        Provider provider = providerService.findByUsername(username).orElseThrow();
        List<Booking> bookings = bookingRepository.findAllByProviderAndDateRange(provider.getId(), startDate, endDate);
        return bookings.stream()
                .map(this::convertToBookingHistoryResponse).toList();
    }

    private BookingHistoryResponse convertToBookingHistoryResponse(Booking booking) {
        return new BookingHistoryResponse(
                booking.getBookingNumber(),
                booking.getAppointment().getDate().toString(),
                booking.getStatus()
        );
    }

    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }

    public List<BookingResponseCreate> listForStaff() {
        return bookingRepository.findByStatus(BookingStatus.CONFIRMED).stream()
                .map(bookingMapper::toResponse)
                .toList();
    }


}