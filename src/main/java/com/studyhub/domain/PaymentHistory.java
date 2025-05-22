package com.studyhub.domain;

import com.studyhub.domain.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

//결제 기록 엔티티

@Entity
@Table(name = "payment_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_history_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Column(nullable = false)
    private int amount;

    @Column(name = "paid_at", nullable = false)
    private LocalDateTime paidAt;

    @OneToOne(optional = true)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}