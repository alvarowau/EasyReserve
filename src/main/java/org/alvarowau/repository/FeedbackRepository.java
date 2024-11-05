package org.alvarowau.repository;

import org.alvarowau.model.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByBookingCustomerUsername(String username);
    List<Feedback> findByBookingAppointmentServiceScheduleServiceOfferingName(String serviceOfferingName);
    List<Feedback> findByBookingAppointmentServiceScheduleServiceOfferingProviderUsername(String providerUsername);
    @Query("SELECT AVG(CASE WHEN f.rating IS NOT NULL THEN f.rating ELSE 0 END) " +
            "FROM Feedback f JOIN f.booking b JOIN b.appointment a JOIN a.serviceSchedule s " +
            "JOIN s.serviceOffering o WHERE o.provider.username = :providerUsername")
    BigDecimal findAverageRatingByProviderUsername(@Param("providerUsername") String providerUsername);
    @Query("SELECT DISTINCT f FROM Feedback f " +
            "JOIN f.booking b " +
            "JOIN b.appointment a " +
            "JOIN a.serviceSchedule s " +
            "JOIN s.serviceOffering o " +
            "WHERE o.name = :serviceOfferingName")
    List<Feedback> findDistinctByServiceOfferingName(@Param("serviceOfferingName") String serviceOfferingName);


}