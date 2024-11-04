package org.alvarowau.repository;

import org.alvarowau.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByBookingNumber(String bookingNumber);
    List<Booking> findByCustomerUsername(String username);

    @Query("SELECT b FROM Booking b " +
            "JOIN b.appointment a " +
            "JOIN a.serviceSchedule ss " +
            "JOIN ss.serviceOffering so " +
            "JOIN so.provider p " +
            "WHERE p.id = :providerId " +
            "AND a.date BETWEEN :startDate AND :endDate")
    List<Booking> findAllByProviderAndDateRange(@Param("providerId") Long providerId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}
