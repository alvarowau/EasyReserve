package org.alvarowau;

import org.alvarowau.service.AppointmentSlotManagementService;
import org.alvarowau.user.repository.CustomerRepository;
import org.alvarowau.user.repository.ProviderRepository;
import org.alvarowau.repository.ServiceOfferingRepository;
import org.alvarowau.repository.ServiceScheduleRepository;
import org.alvarowau.repository.AppointmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EasyReserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyReserveApplication.class, args);
    }

    @Bean
    CommandLineRunner init(CustomerRepository customerRepository,
                           ProviderRepository providerRepository,
                           ServiceOfferingRepository serviceOfferingRepository,
                           ServiceScheduleRepository serviceScheduleRepository,
                           AppointmentRepository appointmentRepository,
                           PasswordEncoder passwordEncoder,
                           AppointmentSlotManagementService appointmentSlotManagementService) {
        return args -> {
//            // Crear un UserProfile para Provider
//            UserProfile providerProfile = UserProfile.builder()
//                    .personalInfo(new PersonalInfo("Jane", "Smith", "987654321"))
//                    .contactInfo(new ContactInfo("555-9876", "555-4321", "jane.smith@example.com"))
//                    .address(new Address("456 Elm St", "Metropolis", "NY", "10001", "USA"))
//                    .build();
//
//            // Crear un Provider
//            Provider provider = new Provider();
//            provider.setUsername("provider1");
//            provider.setPassword(passwordEncoder.encode("1234"));
//            provider.setEmail("provider1@example.com");
//            provider.setRole(RoleEnum.PROVIDER);
//            provider.setEnabled(true);
//            provider.setUserProfile(providerProfile);
//            providerRepository.save(provider);
//
//            // Crear un ServiceOffering y guardarlo en el repositorio
//            ServiceOffering serviceOffering = new ServiceOffering();
//            serviceOffering.setName("Basic Service");
//            serviceOffering.setDuration(60); // Duración de 60 minutos
//            serviceOffering.setProvider(provider);
//            serviceOffering = serviceOfferingRepository.save(serviceOffering); // Guardar y actualizar la instancia
//
//            // Crear horarios y slots de tiempo para lunes y martes
//            ServiceSchedule mondaySchedule = new ServiceSchedule();
//            mondaySchedule.setDay(DayOfWeek.MONDAY);
//            mondaySchedule.setServiceOffering(serviceOffering);
//
//            TimeSlot mondayMorningSlot = new TimeSlot(null, LocalTime.of(9, 0), LocalTime.of(13, 0), mondaySchedule, true);
//            TimeSlot mondayAfternoonSlot = new TimeSlot(null, LocalTime.of(15, 0), LocalTime.of(19, 0), mondaySchedule, true);
//            mondaySchedule.setTimeSlots(Arrays.asList(mondayMorningSlot, mondayAfternoonSlot));
//
//            ServiceSchedule tuesdaySchedule = new ServiceSchedule();
//            tuesdaySchedule.setDay(DayOfWeek.TUESDAY);
//            tuesdaySchedule.setServiceOffering(serviceOffering);
//
//            TimeSlot tuesdaySlot = new TimeSlot(null, LocalTime.of(9, 0), LocalTime.of(16, 0), tuesdaySchedule, true);
//            tuesdaySchedule.setTimeSlots(Arrays.asList(tuesdaySlot));
//
//            // Guardar los horarios en el repositorio y actualizarlos
//            mondaySchedule = serviceScheduleRepository.save(mondaySchedule);
//            tuesdaySchedule = serviceScheduleRepository.save(tuesdaySchedule);
//
//            // Generar y guardar citas en el repositorio
//            List<Appointment> mondayAppointments = horarioTreatment.generateAvailableAppointments(mondaySchedule);
//            List<Appointment> tuesdayAppointments = horarioTreatment.generateAvailableAppointments(tuesdaySchedule);
//
//            // Guardar todas las citas generadas
//            for(Appointment a: mondayAppointments){
//                appointmentRepository.save(a);
//            }
//
//            for(Appointment a: tuesdayAppointments){
//                appointmentRepository.save(a);
//            }
//
//
//            System.out.println("Datos de prueba insertados y citas generadas con éxito.");
        };
    }
}
