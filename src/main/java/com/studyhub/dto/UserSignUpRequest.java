package com.studyhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequest {
    private String username;
    private String email;
    private String password;
    private String phone;
    private String businessNumber; // 사업자만 입력
    private String userType; // "USER" 또는 "OWNER"
}