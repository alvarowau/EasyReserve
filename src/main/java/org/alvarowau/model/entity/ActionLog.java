package org.alvarowau.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.alvarowau.model.enums.ActionType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "action_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ActionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActionType actionType;  // Cambiado de 'action' a 'actionType'

    @Column(nullable = false)
    private Long userId;            // Cambiado de 'username' a 'userId'

    @Column(nullable = false)
    private Long affectedUserId;      // Cambiado de 'targetUsername' a 'targetUserId'

    @Column(nullable = false)
    private String reason;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime timestamp;
}