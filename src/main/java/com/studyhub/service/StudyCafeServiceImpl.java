package com.studyhub.service;

import com.studyhub.domain.StudyCafe;
import com.studyhub.repository.StudyCafeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
    public void registerCafe(StudyCafe cafe) {
        studyCafeRepository.save(cafe);
    }

    @Override
    public List<StudyCafe> getCafesByOwner(Long ownerId) {
        return studyCafeRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<StudyCafe> getAllCafes() {
        return studyCafeRepository.findAll();
    }

    @Override
    @Transactional
    public StudyCafe updateCafe(StudyCafe cafe) {
        StudyCafe existing = studyCafeRepository.findById(cafe.getId())
                .orElseThrow(() -> new EntityNotFoundException("스터디카페가 존재하지 않습니다."));

        if (!existing.getOwner().getId().equals(cafe.getOwner().getId())) {
            throw new AccessDeniedException("해당 스터디카페의 소유자가 아닙니다.");
        }

        existing.setName(cafe.getName());
        existing.setAddress(cafe.getAddress());
        existing.setLatitude(cafe.getLatitude());
        existing.setLongitude(cafe.getLongitude());
        existing.setContact(cafe.getContact());
        existing.setBusinessHour(cafe.getBusinessHour());
        existing.setNotice(cafe.getNotice());
        existing.setReservationCheckMessage(cafe.getReservationCheckMessage());

        return existing;
    }

    @Override
    @Transactional
    public void deleteCafe(Long cafeId, Long requesterOwnerId) {
        StudyCafe cafe = studyCafeRepository.findById(cafeId)
                .orElseThrow(() -> new EntityNotFoundException("스터디카페가 존재하지 않습니다."));

        if (!cafe.getOwner().getId().equals(requesterOwnerId)) {
            throw new AccessDeniedException("해당 스터디카페의 소유자가 아닙니다.");
        }

        studyCafeRepository.delete(cafe);
    }

    // 스터디룸
    @Override
    public StudyCafe getCafeById(Long cafeId) {
        return studyCafeRepository.findById(cafeId)
                .orElseThrow(() -> new EntityNotFoundException("스터디카페를 찾을 수 없습니다."));
    }
}
