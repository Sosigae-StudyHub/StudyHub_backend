package com.studyhub.controller;

import com.studyhub.domain.User;
import com.studyhub.domain.enums.UserType;
import com.studyhub.dto.UserSignUpRequest;
import com.studyhub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.studyhub.dto.LoginRequest;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequest request) {
        if (userService.findUserByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("이미 등록된 이메일입니다.");
        }

        if ("OWNER".equals(request.getUserType()) &&
                userService.checkBusinessNumberExists(request.getBusinessNumber())) {
            return ResponseEntity.badRequest().body("이미 등록된 사업자번호입니다.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .userType(UserType.valueOf(request.getUserType()))
                .businessNumber(request.getBusinessNumber())
                .point(0)
                .build();

        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
    }

    @GetMapping("/duplicationEmail")
    public ResponseEntity<Boolean> checkEmail(@RequestParam String email) {
        boolean available = userService.findUserByEmail(email) == null;
        System.out.println("[중복확인] email=" + email + ", available=" + available); // 로그 찍기
        return ResponseEntity.status(200).body(available); // 명시적으로 200 + true/false 응답
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = userService.validateLogin(request.getEmail(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute("userId", user.getId());
        session.setAttribute("userType", user.getUserType());
        session.setAttribute("username", user.getUsername());

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", user.getId());
        responseBody.put("email", user.getEmail());
        responseBody.put("userType", user.getUserType().name());

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getSessionInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        User user = userService.findById(userId);

        Map<String, Object> info = new HashMap<>();
        info.put("userId", user.getId());
        info.put("username", user.getUsername());
        info.put("phone", user.getPhone());
        info.put("userType", user.getUserType());

        return ResponseEntity.ok(info);
    }


    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 필요");

        User user = userService.findById(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("name", user.getUsername());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());
        response.put("userType", user.getUserType().name());
        if (user.getUserType().equals(UserType.OWNER)) {
            response.put("businessNumber", user.getBusinessNumber());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(HttpSession session, @RequestBody Map<String, String> payload) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");

        String currentPassword = payload.get("currentPassword");
        String newPassword = payload.get("newPassword");

        userService.changePassword(userId, currentPassword, newPassword);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    @PostMapping("/change-phone")
    public ResponseEntity<?> changePhone(HttpSession session, @RequestBody Map<String, String> payload) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");

        String newPhone = payload.get("newPhone");
        userService.changePhone(userId, newPhone);
        return ResponseEntity.ok("전화번호가 변경되었습니다.");
    }
}