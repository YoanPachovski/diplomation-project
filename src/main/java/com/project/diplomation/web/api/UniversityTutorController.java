package com.project.diplomation.web.api;

import com.project.diplomation.data.models.dto.CreateUniversityTutorDTO;
import com.project.diplomation.data.models.dto.UniversityTutorDTO;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.models.enums.PositionType;
import com.project.diplomation.exception.UniversityTutorNotFoundException;
import com.project.diplomation.service.UniversityTutorService;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/uni-tutors")
public class UniversityTutorController {
    private final UniversityTutorService universityTutorService;
    private final MapperUtil mapperUtil;

    @PostMapping("/create")
    public CreateUniversityTutorDTO createUniversityTutorDTO(@RequestBody CreateUniversityTutorDTO universityTutorDTO) {
        return this.universityTutorService.createUniversityTutorDTO(mapperUtil.getModelMapper().map(universityTutorDTO, UniversityTutor.class));
    }
    @GetMapping("/{id}")
    public UniversityTutorDTO getUniTutor(@PathVariable long id){
        try {
            return this.universityTutorService.getUniversityTutor(id);
        } catch (UniversityTutorNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "University Tutor Not Found", exception);
        }
    }
    @GetMapping("/by-name/{name}")
    public List<UniversityTutorDTO> getUniTutorByName(@PathVariable String name) {
        try {
            return this.universityTutorService.getUniversityTutorByName(name);
        } catch (UniversityTutorNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "University Tutor with such name Not Found", exception);
        }
    }
    @GetMapping("/by-position_type/{positionType}")
    public List<UniversityTutorDTO> getUniTutorsByPositionType(@PathVariable PositionType positionType) {
        try {
            return this.universityTutorService.getUniversityTutorByPositionType(positionType);
        } catch (UniversityTutorNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "University Tutor with such position type Not Found", exception);
        }
    }
    @GetMapping("/all")
    public List<UniversityTutorDTO> getAllUniversityTutors() {
        return this.universityTutorService.getAllUniversityTutors();
    }

    @PutMapping("/update/{id}")
    public void updateUniTutor(@PathVariable long id, @RequestBody UniversityTutor uniTutor) {
        try {
            this.universityTutorService.updateUniTutor(uniTutor, id);
        } catch (UniversityTutorNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "University Tutor to be updated Not Found", exception);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUniTutor(@PathVariable long id) {
        try {
            this.universityTutorService.deleteUniTutor(id);
        } catch (UniversityTutorNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "University Tutor to be deleted Not Found", exception);
        }
    }
}
