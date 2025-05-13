package com.studyhub.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "available_time_blocks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AvailableTimeBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "time_seq")
    @SequenceGenerator(name = "time_seq", sequenceName = "available_time_blocks_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "study_room_id", nullable = false)
    private StudyRoom studyRoom;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek; // Enum으로도 가능

    @Column(nullable = false)
    private int hour;

    @Column(name = "is_available")
    private boolean isAvailable;
}