package com.studyhub.service;

import com.studyhub.domain.StudyCafe;
import com.studyhub.dto.StudyCafeSimpleDto;
import com.studyhub.repository.StudyCafeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyCafeServiceImpl implements StudyCafeService {

    private final StudyCafeRepository studyCafeRepository;

    @Override
    public StudyCafeSimpleDto getCafeById(Long id) {
        StudyCafe cafe = studyCafeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카페 없음"));
        return new StudyCafeSimpleDto(cafe);
    }

    @Override
    public List<StudyCafeSimpleDto> getAllCafes() {
        return studyCafeRepository.findAll().stream()
                .map(c -> new StudyCafeSimpleDto(
                        c.getId(), c.getName(), c.getAddress(), c.getLatitude(), c.getLongitude()))
                .toList();
    }
}
