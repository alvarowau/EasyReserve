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



            System.out.println("Datos de prueba insertados con éxito.");
        };
    }
}
