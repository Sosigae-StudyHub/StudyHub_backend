package com.studyhub.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "STUDY_CAFES")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyCafe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cafe_seq")
    @SequenceGenerator(name = "cafe_seq", sequenceName = "STUDY_CAFES_SEQ", allocationSize = 1)
    private Long id;

    private String name;
    private String address;
    private Double latitude;
    private Double longitude;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "BUSINESS_HOUR")
    private String businessHour;

    private String notice;

    @Column(name = "RESERVATION_CHECK_MESSAGE")
    private String reservationCheckMessage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    @JsonBackReference
    private User owner;

    @OneToMany(mappedBy = "studyCafe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StudyRoom> studyRooms;
}
