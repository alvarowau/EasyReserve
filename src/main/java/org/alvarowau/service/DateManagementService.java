package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DateManagementService {

    private final AppointmentRepository appointmentRepository;

    public LocalDate findNextAvailableDate(DayOfWeek desiredDay) {

        LocalDate nextDate = LocalDate.now();

        while (nextDate.getDayOfWeek() != desiredDay) {
            nextDate = nextDate.plusDays(1);
        }

        while (hasAppointmentsInRange(nextDate)) {
            nextDate = nextDate.plusWeeks(1);
        }

        return nextDate;
    }

    private boolean hasAppointmentsInRange(LocalDate date) {
        return appointmentRepository.existsByDate(date);
    }
}
