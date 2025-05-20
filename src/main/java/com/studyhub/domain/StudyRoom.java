package com.studyhub.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "study_rooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudyRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_seq")
    @SequenceGenerator(name = "room_seq", sequenceName = "study_rooms_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "max_capacity")
    private int maxCapacity;

    private int price;

    @Column(name = "equipment_info")
    private String equipmentInfo;

    @Column(name = "pre_reservation_notice")
    private String preReservationNotice;

    @Column(name = "post_reservation_notice")
    private String postReservationNotice;

    @Column(name = "cancel_notice")
    private String cancelNotice;

    @ManyToOne
    @JoinColumn(name = "study_cafe_id", nullable = false)
    @JsonIgnore
    private StudyCafe studyCafe;

    @OneToMany(mappedBy = "studyRoom")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AvailableTimeBlock> availableTimes;
}
