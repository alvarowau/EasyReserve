package org.alvarowau.user.service.util;

import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.entity.BaseUser;
import org.alvarowau.user.repository.CustomerRepository;
import org.alvarowau.user.repository.ProviderRepository;
import org.alvarowau.user.repository.StaffRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserIdFinderService {

    private final CustomerRepository customerRepository;
    private final StaffRepository staffRepository;
    private final ProviderRepository providerRepository;

    public BaseUser findUserById(Long id) {
        BaseUser user = customerRepository.findById(id).orElse(null);
        if (user == null) {
            user = providerRepository.findById(id).orElse(null);
        }
        if (user == null) {
            user = staffRepository.findById(id).orElse(null);
        }
        return user;
    }


}
