package com.studyhub.dto;

import com.studyhub.domain.StudyRoom;
import lombok.Getter;

@Getter
public class StudyRoomResponse {
    private Long id;
    private String name;
    private String description;
    private int maxCapacity;
    private int price;
    private String equipmentInfo;
    private String preReservationNotice;
    private String postReservationNotice;
    private String cancelNotice;

    public StudyRoomResponse(StudyRoom room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription();
        this.maxCapacity = room.getMaxCapacity();
        this.price = room.getPrice();
        this.equipmentInfo = room.getEquipmentInfo();
        this.preReservationNotice = room.getPreReservationNotice();
        this.postReservationNotice = room.getPostReservationNotice();
        this.cancelNotice = room.getCancelNotice();
    }
}
