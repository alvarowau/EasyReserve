package org.alvarowau.repository;

import org.alvarowau.model.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.date = :date")
    boolean existsByDate(@Param("date") LocalDate date);
}
