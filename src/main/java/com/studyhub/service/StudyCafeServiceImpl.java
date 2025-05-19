package com.studyhub.service;

import com.studyhub.domain.StudyCafe;
import com.studyhub.dto.StudyCafeSimpleDto;
import com.studyhub.repository.StudyCafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyCafeServiceImpl implements StudyCafeService {

    private final StudyCafeRepository studyCafeRepository;

    @Autowired
    public StudyCafeServiceImpl(StudyCafeRepository studyCafeRepository) {
        this.studyCafeRepository = studyCafeRepository;
    }

    @Override
    public List<StudyCafe> getAllCafes() {
        return studyCafeRepository.findAll();
    }
}
