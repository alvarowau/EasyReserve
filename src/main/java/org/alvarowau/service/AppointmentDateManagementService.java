package org.alvarowau.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AppointmentDateManagementService {

    private final AppointmentRepository appointmentRepository;

    public LocalDate findNextAvailableDateForAppointment(DayOfWeek desiredDay) {

        LocalDate nextDate = LocalDate.now();

        while (nextDate.getDayOfWeek() != desiredDay) {
            nextDate = nextDate.plusDays(1);
        }

        while (hasAppointmentsOnDate(nextDate)) {
            nextDate = nextDate.plusWeeks(1);
        }

        return nextDate;
    }

    private boolean hasAppointmentsOnDate(LocalDate date) {
        return appointmentRepository.existsByDate(date);
    }
}