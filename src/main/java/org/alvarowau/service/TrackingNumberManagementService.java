package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.model.entity.TrackingNumber;
import org.alvarowau.repository.TrackingNumberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TrackingNumberManagementService {

    private final TrackingNumberRepository trackingNumberRepository;

    public synchronized int getNextTrackingNumberForGeneration() {
        TrackingNumber trackingNumber = trackingNumberRepository.findById(1L)
                .orElse(new TrackingNumber());
        int newTrackingNumber = trackingNumber.getLastNumber() + 1;
        trackingNumber.setLastNumber(newTrackingNumber);
        trackingNumberRepository.save(trackingNumber);
        return newTrackingNumber;
    }

    public String generateTrackingNumberFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = date.format(formatter);
        int sequenceNumber = getNextTrackingNumberForGeneration();
        return String.format("TRACK-%s-%04d", datePart, sequenceNumber);
    }
}