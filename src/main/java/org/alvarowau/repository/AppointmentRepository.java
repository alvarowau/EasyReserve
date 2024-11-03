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

    // Método existente que filtra por el nombre de proveedor
    List<Appointment> findByServiceSchedule_ServiceOffering_Provider_UsernameAndIsAvailableTrue(String username);

    // Nuevo método que filtra por el nombre del ServiceOffering
    List<Appointment> findByServiceSchedule_ServiceOffering_NameAndIsAvailableTrue(String serviceOfferingName);

    List<Appointment> findByIsAvailableTrueAndDate(@Param("date") LocalDate date);

    @Query("SELECT a FROM Appointment a WHERE a.isAvailable = true AND a.date BETWEEN :startDate AND :endDate")
    List<Appointment> findByIsAvailableTrueAndDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
