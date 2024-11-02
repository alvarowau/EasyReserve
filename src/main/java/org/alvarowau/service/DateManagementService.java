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
        // Obtener la fecha actual
        LocalDate nextDate = LocalDate.now();

        // Calcular la próxima fecha para el día de la semana deseado
        while (nextDate.getDayOfWeek() != desiredDay) {
            nextDate = nextDate.plusDays(1); // Avanzar un día
        }

        // Comprobar si hay citas en la fecha calculada y avanzar si es necesario
        while (hasAppointmentsInRange(nextDate)) {
            nextDate = nextDate.plusWeeks(1); // Avanzar una semana
        }

        return nextDate; // Retorna la próxima fecha disponible
    }

    private boolean hasAppointmentsInRange(LocalDate date) {
        // Aquí implementamos la lógica para verificar si hay citas en la base de datos para esa fecha
        // Puedes utilizar un método en AppointmentRepository para realizar la consulta.
        return appointmentRepository.existsByDate(date); // Suponiendo que existe un método en el repositorio
    }
}
