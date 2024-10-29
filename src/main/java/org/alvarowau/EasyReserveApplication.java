package org.alvarowau;

import org.alvarowau.user.model.entity.RoleEnum;
import org.alvarowau.user.model.entity.UserEntity;
import org.alvarowau.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class EasyReserveApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyReserveApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verificar si la tabla ya tiene datos para evitar duplicados
            if (userRepository.count() == 0) {
                UserEntity adminUser = UserEntity.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("1234")) // Encriptar la contraseña
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .role(RoleEnum.ADMIN)
                        .build();

                UserEntity customerUser = UserEntity.builder()
                        .username("customer")
                        .password(passwordEncoder.encode("1234")) // Encriptar la contraseña
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .role(RoleEnum.CUSTOMER)
                        .build();

                UserEntity providerUser = UserEntity.builder()
                        .username("provider")
                        .password(passwordEncoder.encode("1234")) // Encriptar la contraseña
                        .isEnabled(true)
                        .accountNoExpired(true)
                        .accountNoLocked(true)
                        .credentialNoExpired(true)
                        .role(RoleEnum.PROVIDER)
                        .build();

                // Guardar todos los usuarios en la base de datos
                userRepository.saveAll(List.of(adminUser, customerUser, providerUser));
            }
        };
    }

}
