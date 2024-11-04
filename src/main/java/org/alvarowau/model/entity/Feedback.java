package org.alvarowau.model.entity;


import jakarta.persistence.*;
import lombok.*;
import org.alvarowau.model.enums.FeedbackRating;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    private String comment;


    private FeedbackRating rating;
}
