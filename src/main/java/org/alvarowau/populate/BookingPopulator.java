package org.alvarowau.populate;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.dto.booking.BookingRequestTrackingNumber;
import org.alvarowau.model.dto.booking.BookingResponseCreate;
import org.alvarowau.model.dto.booking.CancelBookingRequest;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.Booking;
import org.alvarowau.repository.AppointmentRepository;
import org.alvarowau.repository.BookingRepository;
import org.alvarowau.service.BookingService;
import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class BookingPopulator {
    private final BookingRepository bookingRepository;
    private final BookingService bookingService;
    private final AppointmentRepository appointmentRepository;
    private final CustomerRepository customerRepository;
    private final List<String> cancellationReasons = ListReason();

    public List<BookingResponseCreate> createBooking() {
        List<Appointment> appointments = new ArrayList<>(appointmentRepository.findAll());
        List<Customer> customers = customerRepository.findAll();
        List<BookingRequestTrackingNumber> bookings = new ArrayList<>();

        Random random = new Random();

        for (Customer customer : customers) {
            for (int i = 0; i < 15; i++) {
                if (appointments.isEmpty()) {
                }

                int randomIndex = random.nextInt(appointments.size());
                Appointment selectedAppointment = appointments.get(randomIndex);

                BookingRequestTrackingNumber booking = new BookingRequestTrackingNumber(
                        selectedAppointment.getTrackingNumber(),
                        customer.getUsername()
                );

                bookings.add(booking);
                appointments.remove(randomIndex);
            }
        }
        List<BookingResponseCreate> bookingResponseCreateList = new ArrayList<>();
        for (BookingRequestTrackingNumber bookingRequestTrackingNumber : bookings) {
            bookingResponseCreateList.add(bookingService.createBookingByTrackingNumberAppointment(bookingRequestTrackingNumber));
        }
        return bookingResponseCreateList;
    }

    public void cancelBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<CancelBookingRequest> cancelRequests = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 130; i++) {
            if (bookings.isEmpty()) {
                break; // Salir si no hay más bookings
            }

            int randomIndex = random.nextInt(bookings.size());
            Booking selectedBooking = bookings.get(randomIndex);
            String reason = getRandomCancellationReason();
            CancelBookingRequest cancelRequest = new CancelBookingRequest(
                    selectedBooking.getBookingNumber(),
                    selectedBooking.getCustomer().getUsername(),
                    reason
            );

            cancelRequests.add(cancelRequest);

            bookings.remove(randomIndex);
        }

        for(CancelBookingRequest cancel: cancelRequests){
            bookingService.cancelBookingByUser(cancel);
        }
    }

    private String getRandomCancellationReason() {
        Random random = new Random();
        return cancellationReasons.get(random.nextInt(cancellationReasons.size()));
    }

    private List<String> ListReason(){
        return List.of(
                "El cliente canceló por motivos personales.",
                "El proveedor no está disponible en la fecha solicitada.",
                "La cita fue reprogramada para otro día.",
                "El cliente no podrá asistir por motivos de salud.",
                "El proveedor tiene una emergencia imprevista.",
                "El cliente encontró otro proveedor para el servicio.",
                "El cliente olvidó cancelar la cita a tiempo.",
                "Problemas de transporte o logística del cliente.",
                "La cita fue agendada por error.",
                "El proveedor cerró temporalmente.",
                "El cliente cambió de planes de última hora.",
                "El proveedor necesita tiempo adicional para preparación.",
                "El cliente no cumple con los requisitos previos al servicio.",
                "Condiciones climáticas adversas.",
                "Cancelación debido a circunstancias de fuerza mayor."
        );
    }
}