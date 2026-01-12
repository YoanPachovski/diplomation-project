package com.project.diplomation.data.repositories;

import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.models.enums.PositionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UniversityTutorRepo extends JpaRepository<UniversityTutor, Long> {
    List<UniversityTutor> findByName(String name);
    List<UniversityTutor> findUniversityTutorByPositionType(PositionType positionType);
}
