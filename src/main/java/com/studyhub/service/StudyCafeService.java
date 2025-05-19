package com.studyhub.service;

import com.studyhub.domain.StudyCafe;
import com.studyhub.dto.StudyCafeSimpleDto;
import java.util.List;

public interface StudyCafeService {
    List<StudyCafe> getAllCafes(); // ← DTO 아님
}