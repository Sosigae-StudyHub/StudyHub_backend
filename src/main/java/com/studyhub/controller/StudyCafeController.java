package com.studyhub.controller;

import com.studyhub.domain.StudyCafe;
import com.studyhub.dto.StudyCafeSimpleDto;
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
    public List<StudyCafeSimpleDto> getAllCafes() {
        List<StudyCafe> cafes = studyCafeRepository.findAll();
        System.out.println("✅ 카페 수: " + cafes.size());

        return cafes.stream()
                .map(c -> new StudyCafeSimpleDto(
                        c.getId(),
                        c.getName(),
                        c.getAddress(),
                        c.getLatitude(),
                        c.getLongitude()
                ))
                .toList();
    }

}
