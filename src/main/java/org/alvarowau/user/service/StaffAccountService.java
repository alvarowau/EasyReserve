package org.alvarowau.user.service;

import org.alvarowau.service.ActionLogService;
import org.alvarowau.user.model.entity.Staff;
import org.alvarowau.user.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffAccountService extends AbstractUserAccountService<Staff> {

    private final StaffRepository repository;

    @Override
    public Optional<Staff> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Staff> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public StaffAccountService(StaffRepository repository, ActionLogService actionLogService) {
        super(actionLogService);
        this.repository = repository;
    }

    @Override
    public Optional<Staff> findByEmail(String email) {
        return repository.findByEmail(email);
    }


    public Optional<Long> findIdByUsername(String username) {
        return repository.findIdByUsername(username);
    }

    @Override
    protected Staff saveEntity(Staff entity) {
        return repository.save(entity);
    }
}