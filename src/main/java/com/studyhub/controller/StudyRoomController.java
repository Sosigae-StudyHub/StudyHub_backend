package com.studyhub.controller;

import com.studyhub.dto.StudyRoomResponse;
import com.studyhub.service.StudyRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class StudyRoomController {

    private final StudyRoomService studyRoomService;

    @GetMapping("/by-cafe/{cafeId}")
    public ResponseEntity<List<StudyRoomResponse>> getRoomsByCafeId(@PathVariable Long cafeId) {
        List<StudyRoomResponse> response = studyRoomService.getRoomsByCafeId(cafeId);
        return ResponseEntity.ok(response);
    }
}
