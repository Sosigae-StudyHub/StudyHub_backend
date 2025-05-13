package com.studyhub.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "study_cafes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudyCafe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cafe_seq")
    @SequenceGenerator(name = "cafe_seq", sequenceName = "study_cafes_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private double latitude;
    private double longitude;
    private String contact;

    @Column(name = "business_hour")
    private String businessHour;

    private String notics;

    @Column(name = "reservation_check_message")
    private String reservationCheckMessage;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "studyCafe")
    private List<StudyRoom> rooms;

    @OneToMany(mappedBy = "studyCafe")
    private List<Revenue> revenues;
}