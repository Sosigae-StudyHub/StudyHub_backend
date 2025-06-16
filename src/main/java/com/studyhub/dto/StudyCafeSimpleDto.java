package com.studyhub.dto;

import com.studyhub.domain.StudyCafe;
import lombok.Getter;

@Getter
public class StudyCafeSimpleDto {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String contact;
    private String notice;

    // ✅ 직접 필드 하나씩 넣는 생성자
    public StudyCafeSimpleDto(Long id, String name, String address, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // ✅ 단건 조회 시 사용되는 StudyCafe 객체 기반 생성자
    public StudyCafeSimpleDto(StudyCafe cafe) {
        this.id = cafe.getId();
        this.name = cafe.getName();
        this.address = cafe.getAddress();
        this.latitude = cafe.getLatitude();
        this.longitude = cafe.getLongitude();
        this.contact = cafe.getContact();
        this.notice = cafe.getNotice();
    }
}
