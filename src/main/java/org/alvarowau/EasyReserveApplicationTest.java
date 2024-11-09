package org.alvarowau;

import lombok.Getter;
import lombok.Setter;
import org.alvarowau.model.entity.Appointment;
import org.alvarowau.model.entity.ServiceOffering;
import org.alvarowau.model.entity.ServiceSchedule;
import org.alvarowau.model.entity.TimeSlot;
import org.alvarowau.service.AppointmentSlotManagementService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class EasyReserveApplicationTest {

    public static void main(String[] args) {
        // Iniciar la aplicación Spring
        ApplicationContext context = SpringApplication.run(EasyReserveApplicationTest.class, args);

        // Obtener el bean HorarioTreatment
        AppointmentSlotManagementService appointmentSlotManagementService = context.getBean(AppointmentSlotManagementService.class);

        // Crear un ServiceOffering
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setName("Basic Service");
        serviceOffering.setDuration(60); // Duración de 60 minutos

        // Crear horarios y slots de tiempo para lunes y martes
        ServiceSchedule mondaySchedule = new ServiceSchedule();
        mondaySchedule.setDay(DayOfWeek.MONDAY);
        mondaySchedule.setServiceOffering(serviceOffering);

        TimeSlot mondayMorningSlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(13, 0), true);
        TimeSlot mondayAfternoonSlot = new TimeSlot(LocalTime.of(15, 0), LocalTime.of(19, 0), true);
        mondaySchedule.setTimeSlots(Arrays.asList(mondayMorningSlot, mondayAfternoonSlot));

        ServiceSchedule tuesdaySchedule = new ServiceSchedule();
        tuesdaySchedule.setDay(DayOfWeek.TUESDAY);
        tuesdaySchedule.setServiceOffering(serviceOffering);

        TimeSlot tuesdaySlot = new TimeSlot(LocalTime.of(9, 0), LocalTime.of(16, 0), true);
        tuesdaySchedule.setTimeSlots(List.of(tuesdaySlot));

        // Generar citas para lunes y martes
        List<Appointment> mondayAppointments = appointmentSlotManagementService.generateAvailableAppointments(mondaySchedule);
        List<Appointment> tuesdayAppointments = appointmentSlotManagementService.generateAvailableAppointments(tuesdaySchedule);

        // Imprimir las citas generadas
        System.out.println("Citas generadas para Lunes:");
        for (Appointment appointment : mondayAppointments) {
            System.out.println("Cita: " + appointment.getTrackingNumber() +
                    ", Inicio: " + appointment.getStartTime() +
                    ", Fin: " + appointment.getEndTime());
        }

        System.out.println("\nCitas generadas para Martes:");
        for (Appointment appointment : tuesdayAppointments) {
            System.out.println("Cita: " + appointment.getTrackingNumber() +
                    ", Inicio: " + appointment.getStartTime() +
                    ", Fin: " + appointment.getEndTime());
        }

        // Total de citas generadas
        int totalAppointments = mondayAppointments.size() + tuesdayAppointments.size();
        System.out.println("\nTotal de citas generadas: " + totalAppointments);
    }
}
