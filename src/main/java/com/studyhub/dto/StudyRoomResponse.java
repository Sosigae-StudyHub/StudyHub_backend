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

    public StudyRoomResponse(StudyRoom room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription(); // ✅ 수정됨
        this.maxCapacity = room.getMaxCapacity();
        this.price = room.getPrice();
    }
}
