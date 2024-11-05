package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.entity.TrackingNumber;
import org.alvarowau.repository.TrackingNumberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TrackingNumberService {

    private final TrackingNumberRepository trackingNumberRepository;

    // Método sincronizado para obtener el siguiente número de seguimiento
    public synchronized int getNextTrackingNumber() {
        TrackingNumber trackingNumber = trackingNumberRepository.findById(1L)
                .orElse(new TrackingNumber());
        int newTrackingNumber = trackingNumber.getLastNumber() + 1;
        trackingNumber.setLastNumber(newTrackingNumber);
        trackingNumberRepository.save(trackingNumber); // Actualiza en la base de datos
        return newTrackingNumber;
    }

    // Método para generar el número de seguimiento
    public String generateTrackingNumber(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = date.format(formatter); // Formato de la fecha
        int sequenceNumber = getNextTrackingNumber(); // Obtiene el siguiente número de secuencia
        return String.format("TRACK-%s-%04d", datePart, sequenceNumber); // Número de seguimiento con formato
    }
}