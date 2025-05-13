package com.studyhub.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "revenues")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revenue_seq")
    @SequenceGenerator(name = "revenue_seq", sequenceName = "revenues_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_cafe_id", nullable = false)
    private StudyCafe studyCafe;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @Column(nullable = false)
    private int amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
