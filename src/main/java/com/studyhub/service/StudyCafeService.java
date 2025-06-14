package com.studyhub.service;

import com.studyhub.domain.StudyCafe;
import com.studyhub.dto.StudyCafeSimpleDto;
import java.util.List;

public interface StudyCafeService {
    // 스터디카페
    void registerCafe(StudyCafe cafe);
    List<StudyCafe> getCafesByOwner(Long ownerId); // ← DTO 아님
    List<StudyCafe> getAllCafes(); // ← DTO 아님
    List<StudyCafeSimpleDto> getAllCafesSimple(); //지우
    StudyCafe updateCafe(StudyCafe cafe);
    void deleteCafe(Long cafeId, Long ownerId);

    // 스터디룸
    StudyCafe getCafeById(Long cafeId);
    StudyCafeSimpleDto getCafeSimpleById(Long id);

}