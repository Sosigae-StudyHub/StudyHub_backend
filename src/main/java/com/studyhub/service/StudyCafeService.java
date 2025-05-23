package com.studyhub.service;

import com.studyhub.domain.StudyCafe;
import com.studyhub.dto.StudyCafeSimpleDto;
import java.util.List;

public interface StudyCafeService {
    StudyCafeSimpleDto getCafeById(Long id);
    List<StudyCafeSimpleDto> getAllCafes(); // ← DTO 아님
}