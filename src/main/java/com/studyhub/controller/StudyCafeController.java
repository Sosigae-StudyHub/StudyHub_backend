package com.studyhub.controller;

import com.studyhub.dto.StudyCafeSimpleDto;
import com.studyhub.service.StudyCafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
public class StudyCafeController {

    private final StudyCafeService studyCafeService;

    @GetMapping("/{id}")
    public ResponseEntity<StudyCafeSimpleDto> getCafeById(@PathVariable Long id) {
        return ResponseEntity.ok(studyCafeService.getCafeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<StudyCafeSimpleDto>> getAllCafes() {
        return ResponseEntity.ok(studyCafeService.getAllCafes());
    }
}
