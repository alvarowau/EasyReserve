package org.alvarowau.user.repository;

import org.alvarowau.user.model.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long>, BaseUserRepository<Staff> {
    @Query("SELECT s.id FROM Staff s WHERE s.username = :username")
    Optional<Long> findIdByUsername(@Param("username") String username);
}
