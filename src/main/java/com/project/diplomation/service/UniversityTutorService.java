package com.project.diplomation.service;

import com.project.diplomation.data.models.dto.CreateUniversityTutorDTO;
import com.project.diplomation.data.models.dto.UniversityTutorDTO;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.models.enums.PositionType;
import com.project.diplomation.data.repositories.UniversityTutorRepo;
import com.project.diplomation.exception.UniversityTutorNotFoundException;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityTutorService {
    private final UniversityTutorRepo universityTutorRepo;
    private final MapperUtil mapperUtil;

    public CreateUniversityTutorDTO createUniversityTutorDTO(UniversityTutor universityTutor) {
        return mapperUtil.getModelMapper()
                .map(this.universityTutorRepo
                        .save(mapperUtil.getModelMapper()
                                .map(universityTutor, UniversityTutor.class)), CreateUniversityTutorDTO.class);

    }
    public UniversityTutorDTO getUniversityTutor(long id) {
        return this.mapperUtil.getModelMapper()
                .map(this.universityTutorRepo.findById(id)
                                .orElseThrow(() -> new UniversityTutorNotFoundException("University tutor with id=" + id + " not found!")),
                        UniversityTutorDTO.class);
    }
    public List<UniversityTutorDTO> getUniversityTutorByName(String name) {
        return this.mapperUtil
                .mapList(this.universityTutorRepo.findByName(name)
                        , UniversityTutorDTO.class);
    }

    public List<UniversityTutorDTO> getUniversityTutorByPositionType(PositionType positionType) {
        return this.mapperUtil
                .mapList(this.universityTutorRepo.findUniversityTutorByPositionType(positionType)
                        , UniversityTutorDTO.class);
    }
    public List<UniversityTutorDTO> getAllUniversityTutors() {
        return this.mapperUtil
                .mapList(this.universityTutorRepo.findAll()
                        , UniversityTutorDTO.class);
    }

    public UniversityTutor updateUniTutor(UniversityTutor universityTutor, long id) {
        return this.universityTutorRepo.findById(id)
                .map(tutor -> {
                    tutor.setName(universityTutor.getName() == null ? tutor.getName() : universityTutor.getName());
                    tutor.setPositionType(universityTutor.getPositionType() == null ? tutor.getPositionType() : universityTutor.getPositionType());
                    return this.universityTutorRepo.save(tutor);
                })
                .orElseThrow(() -> new UniversityTutorNotFoundException("UniTutor with id= " + id + " not found!"));
    }

    public void deleteUniTutor(long id) {
        this.universityTutorRepo.deleteById(id);
    }
}
