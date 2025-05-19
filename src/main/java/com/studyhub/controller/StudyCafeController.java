package com.studyhub.controller;

import com.studyhub.domain.StudyCafe;
import com.studyhub.repository.StudyCafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cafes")
public class StudyCafeController {

    private final StudyCafeRepository studyCafeRepository;

    @Autowired
    public StudyCafeController(StudyCafeRepository studyCafeRepository) {
        this.studyCafeRepository = studyCafeRepository;
    }

    @GetMapping("/all")
    public List<StudyCafe> getAllCafes() {
        List<StudyCafe> cafes = studyCafeRepository.findAll();
        System.out.println("✅ 카페 수: " + cafes.size());
        return cafes;
    }
}
