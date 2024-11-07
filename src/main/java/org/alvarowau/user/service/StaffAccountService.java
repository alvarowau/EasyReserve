package org.alvarowau.user.service;

import org.alvarowau.service.ActionLogManagementService;
import org.alvarowau.user.model.entity.Staff;
import org.alvarowau.user.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StaffAccountService extends AbstractUserAccountService<Staff> {

    private final StaffRepository repository;

    @Override
    public Optional<Staff> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public StaffAccountService(StaffRepository repository, ActionLogManagementService actionLogManagementService) {
        super(actionLogManagementService);
        this.repository = repository;
    }

    public Optional<Long> findIdByUsername(String username) {
        return repository.findIdByUsername(username);
    }

    @Override
    protected Staff saveEntity(Staff entity) {
        return repository.save(entity);
    }
}