package org.alvarowau.repository;

import org.alvarowau.model.entity.ServiceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, Long> {
}
