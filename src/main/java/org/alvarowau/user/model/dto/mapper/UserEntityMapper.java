package org.alvarowau.user.model.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.dto.UserRegistrationRequest;
import org.alvarowau.user.model.entity.Customer;
import org.alvarowau.user.model.entity.Provider;
import org.alvarowau.user.model.entity.Staff;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.alvarowau.user.model.value.UserProfile;
import org.alvarowau.user.model.value.PersonalInfo;
import org.alvarowau.user.model.value.ContactInfo;
import org.alvarowau.model.value.Address;

@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final PasswordEncoder passwordEncoder;


    public Customer convertToCustomer(UserRegistrationRequest userRegistrationRequest) {
        return new Customer(
                userRegistrationRequest.username(),
                encodePassword(userRegistrationRequest.password()),
                userRegistrationRequest.email(),
                createUserProfile(userRegistrationRequest)
        );
    }

    public Provider convertToProvider(UserRegistrationRequest userRegistrationRequest) {
        return new Provider(
                userRegistrationRequest.username(),
                encodePassword(userRegistrationRequest.password()),
                userRegistrationRequest.email(),
                createUserProfile(userRegistrationRequest)
        );
    }

    public Staff convertToStaff(UserRegistrationRequest userRegistrationRequest) {
        return new Staff(
                userRegistrationRequest.username(),
                encodePassword(userRegistrationRequest.password()),
                userRegistrationRequest.email(),
                createUserProfile(userRegistrationRequest)
        );
    }

    private UserProfile createUserProfile(UserRegistrationRequest userRegistrationRequest) {
        // Crear el UserProfile
        return new UserProfile(
                new PersonalInfo(userRegistrationRequest.firstName(), userRegistrationRequest.lastName(), userRegistrationRequest.idDocument()),
                new ContactInfo(userRegistrationRequest.primaryPhone(), userRegistrationRequest.secondaryPhone(), userRegistrationRequest.additionalEmail()),
                new Address(userRegistrationRequest.street(), userRegistrationRequest.city(), userRegistrationRequest.state(), userRegistrationRequest.zipCode(), userRegistrationRequest.country())
        );
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
