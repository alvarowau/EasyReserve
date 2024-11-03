package org.alvarowau.repository;

import org.alvarowau.model.entity.TrackingNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackingNumberRepository extends JpaRepository<TrackingNumber, Long> {
    Optional<TrackingNumber> findById(Long id);
}