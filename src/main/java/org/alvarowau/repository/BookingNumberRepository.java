package org.alvarowau.repository;

import org.alvarowau.model.entity.BookingNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingNumberRepository extends JpaRepository<BookingNumber, Long> {
}