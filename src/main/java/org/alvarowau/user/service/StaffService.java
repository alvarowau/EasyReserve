package org.alvarowau.user.service;

import lombok.RequiredArgsConstructor;
import org.alvarowau.user.model.entity.Staff;
import org.alvarowau.user.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService implements BaseUserService<Staff> {

    private final StaffRepository repository;

    @Override
    public Optional<Staff> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<Staff> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Staff save(Staff staff) {
        return repository.save(staff);
    }
}
