package org.alvarowau.repository;

import org.alvarowau.model.entity.ActionLog;
import org.alvarowau.model.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
    List<ActionLog> findByActionType(ActionType actionType);
}
