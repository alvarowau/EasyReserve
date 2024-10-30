package org.alvarowau;

import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.model.entity.Staff;
import org.alvarowau.user.model.entity.enums.RoleEnum;
import org.alvarowau.model.value.Address;
import org.alvarowau.user.model.value.ContactInfo;
import org.alvarowau.user.model.value.PersonalInfo;
import org.alvarowau.user.model.value.UserProfile;
import org.alvarowau.user.repository.CustomerRepository;
import org.alvarowau.user.repository.ProviderRepository;
import org.alvarowau.user.repository.StaffRepository;
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
                           StaffRepository staffRepository,
                           PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear un UserProfile para Customer
            UserProfile customerProfile = UserProfile.builder()
                    .personalInfo(new PersonalInfo("John", "Doe", "123456789"))
                    .contactInfo(new ContactInfo("555-1234", "555-5678", "john.doe@example.com"))
                    .address(new Address("123 Main St", "Springfield", "IL", "62701", "USA"))
                    .build();

            // Crear un Customer
            Customer customer = new Customer();
            customer.setUsername("customer1");
            customer.setPassword(passwordEncoder.encode("1234"));
            customer.setEmail("customer1@example.com");
            customer.setRole(RoleEnum.CUSTOMER);
            customer.setEnabled(true);
            customer.setUserProfile(customerProfile); // Asigna el UserProfile correspondiente aquí
            customerRepository.save(customer);

            // Crear un UserProfile para Provider
            UserProfile providerProfile = UserProfile.builder()
                    .personalInfo(new PersonalInfo("Jane", "Smith", "987654321"))
                    .contactInfo(new ContactInfo("555-9876", "555-4321", "jane.smith@example.com"))
                    .address(new Address("456 Elm St", "Metropolis", "NY", "10001", "USA"))
                    .build();

            // Crear un Provider
            Provider provider = new Provider();
            provider.setUsername("provider1");
            provider.setPassword(passwordEncoder.encode("1234"));
            provider.setEmail("provider1@example.com");
            provider.setRole(RoleEnum.PROVIDER);
            provider.setEnabled(true);
            provider.setUserProfile(providerProfile); // Asigna el UserProfile correspondiente aquí
            providerRepository.save(provider);

            // Crear un UserProfile para Staff
            UserProfile staffProfile = UserProfile.builder()
                    .personalInfo(new PersonalInfo("Emily", "Johnson", "135792468"))
                    .contactInfo(new ContactInfo("555-2468", null, "emily.johnson@example.com"))
                    .address(new Address("789 Maple St", "Gotham", "NJ", "07001", "USA"))
                    .build();

            // Crear un Staff
            Staff staff = new Staff();
            staff.setUsername("staff1");
            staff.setPassword(passwordEncoder.encode("1234"));
            staff.setEmail("staff1@example.com");
            staff.setRole(RoleEnum.STAFF);
            staff.setEnabled(true);
            staff.setUserProfile(staffProfile); // Asigna el UserProfile correspondiente aquí
            staffRepository.save(staff);

            System.out.println("Datos de prueba insertados con éxito.");
        };
    }
}
