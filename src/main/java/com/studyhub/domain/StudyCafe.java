package com.studyhub.domain;

import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private User owner;
}
