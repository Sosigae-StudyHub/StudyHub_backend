package com.studyhub.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "calendar_events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calendar_seq")
    @SequenceGenerator(name = "calendar_seq", sequenceName = "calendar_events_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;
}