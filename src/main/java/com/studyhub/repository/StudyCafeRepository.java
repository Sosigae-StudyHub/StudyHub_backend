package com.studyhub.repository;

import com.studyhub.domain.StudyCafe;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudyCafeRepository extends JpaRepository<StudyCafe, Long> {
    List<StudyCafe> findByOwnerId(Long ownerId);
}
