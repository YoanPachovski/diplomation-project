package com.project.diplomation.web.api;

import com.project.diplomation.data.models.dto.CreateThesisDTO;
import com.project.diplomation.data.models.dto.ThesisDTO;
import com.project.diplomation.data.models.entities.Application;
import com.project.diplomation.data.models.entities.Thesis;
import com.project.diplomation.data.repositories.ApplicationRepo;
import com.project.diplomation.exception.ThesisNotFoundException;
import com.project.diplomation.service.ThesisService;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theses")
public class ThesisController {
    private final ThesisService thesisService;
    private final MapperUtil mapperUtil;
    private final ApplicationRepo applicationRepo;

    @PostMapping("/create")
    public CreateThesisDTO createThesisDTO(@RequestBody CreateThesisDTO thesisDTO) {
        Application application = applicationRepo.getById(thesisDTO.getApplicationId());
        // Create a new Thesis entity with the fetched foreign keys
        Thesis thesis = new Thesis();
        thesis.setText(thesisDTO.getText());
        thesis.setTitle(thesisDTO.getTitle());
        thesis.setDateOfSubmission(thesisDTO.getDateOfSubmission());
        thesis.setApplication(application);

        // Save the application in the database
        CreateThesisDTO savedThesis = thesisService.createThesisDTO(thesis);

        // Convert and return response as DTO
        return savedThesis;
    }

    @GetMapping("/{id}")
    public ThesisDTO getThesis(@PathVariable long id){
        try {
            return this.thesisService.getThesis(id);
        } catch (ThesisNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Thesis Not Found", exception);
        }
    }

    @GetMapping("/all")
    public List<ThesisDTO> getAllThesis() {
        return this.thesisService.getAllTheses();
    }

    @PutMapping("/update/{id}")
    public ThesisDTO updateThesis(@PathVariable long id, @RequestBody ThesisDTO thesisDTO) {
        try {
            return this.thesisService.updateThesis(thesisDTO, id);
        } catch (ThesisNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Thesis Not Found", exception);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteThesis(@PathVariable long id) {
        try {
            this.thesisService.deleteThesis(id);
        } catch (ThesisNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Thesis Not Found", exception);
        }
    }

    @GetMapping("/by-title/{title}")
    public List<ThesisDTO> getThesesByTitle(@PathVariable String title) {
        return this.thesisService.getThesesByTitle(title);
    }
}
