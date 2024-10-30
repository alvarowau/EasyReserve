package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long>, BaseUserRepository<Staff> {
}
