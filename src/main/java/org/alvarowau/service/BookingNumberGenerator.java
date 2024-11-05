package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.entity.BookingNumber;
import org.alvarowau.repository.BookingNumberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class BookingNumberGenerator {

    private final BookingNumberRepository bookingNumberRepository;

    // Método sincronizado para obtener el siguiente número de reserva
    public synchronized int getNextBookingNumber() {
        BookingNumber bookingNumber = bookingNumberRepository.findById(1L)
                .orElse(new BookingNumber());
        int newBookingNumber = bookingNumber.getLastNumber() + 1;
        bookingNumber.setLastNumber(newBookingNumber);
        bookingNumberRepository.save(bookingNumber); // Actualiza en la base de datos
        return newBookingNumber;
    }

    // Método para generar el número de reserva
    public String generateBookingNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = LocalDate.now().format(formatter); // Formato de la fecha actual
        int sequenceNumber = getNextBookingNumber(); // Obtiene el siguiente número de secuencia
        return String.format("BOOK-%s-%04d", datePart, sequenceNumber); // Número de reserva con formato
    }
}