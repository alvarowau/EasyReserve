package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.config.utils.SecurityContextUtil;
import org.alvarowau.exception.schedule.AppointmentNotFoundException;
import org.alvarowau.exception.user.CustomerNotFoundException;
import org.alvarowau.exception.user.InvalidCustomerException;
import org.alvarowau.model.dto.booking.BookingRequestId;
import org.alvarowau.model.dto.booking.BookingRequestTrackingNumber;
import org.alvarowau.model.dto.booking.BookingResponseCreate;
import org.alvarowau.model.dto.mapper.MapperBooking;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.Booking;
import org.alvarowau.model.enums.BookingStatus;
import org.alvarowau.repository.BookingRepository;
import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingNumberGenerator bookingNumberGenerator;
    private final MapperBooking bookingMapper;
    private final AppointmentService appointmentService;
    private final CustomerService customerService;
    private final SecurityContextUtil securityContextUtil;


    public BookingResponseCreate createBookingByTrackingNumberAppointment(BookingRequestTrackingNumber bookingRequestTrackingNumber){
        Appointment appointment = appointmentService.findByTrackingNumber(bookingRequestTrackingNumber.trackingNumberAppointment())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with tracking number: "
                        + bookingRequestTrackingNumber.trackingNumberAppointment()));
        Customer customer = getCustomer(bookingRequestTrackingNumber.usernameCustomer());

        if (!coincideNombreConContexto(customer.getUsername())) {
            throw new InvalidCustomerException("Customer username does not match the authenticated user.");
        }

        return createBooking(customer, appointment);
    }


    public BookingResponseCreate createBookingByIdAppointment(BookingRequestId bookingRequestId) {
        Appointment appointment = appointmentService.findById(bookingRequestId.id())
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment not found with id: "
                        + bookingRequestId.id()));
        Customer customer = getCustomer(bookingRequestId.usernameCustomer());

        if (!coincideNombreConContexto(customer.getUsername())) {
            throw new InvalidCustomerException("Customer username does not match the authenticated user.");
        }
        return createBooking(customer, appointment);
    }


    private Customer getCustomer(String usernameCustomer) {
        return customerService.findByUsername(usernameCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with username: "
                        + usernameCustomer));

    }

    private boolean coincideNombreConContexto(String username){
        return securityContextUtil.getAuthenticatedUsername().equals(username);
    }


    private BookingResponseCreate createBooking(Customer customer, Appointment appointment) {
        Booking booking = Booking.builder()
                .appointment(appointment)
                .customer(customer)
                .status(BookingStatus.CONFIRMED)
                .bookingNumber(bookingNumberGenerator.generateBookingNumber())
                .build();
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }



}
