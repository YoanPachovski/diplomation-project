package com.project.diplomation.service;

import com.project.diplomation.data.models.dto.CreateThesisDTO;
import com.project.diplomation.data.models.dto.ThesisDTO;
import com.project.diplomation.data.models.entities.Thesis;
import com.project.diplomation.data.repositories.ApplicationRepo;
import com.project.diplomation.data.repositories.ThesisRepo;
import com.project.diplomation.exception.ThesisNotFoundException;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisService {
    private final ThesisRepo thesisRepo;
    private final MapperUtil mapperUtil;
    private final ApplicationRepo applicationRepo;

    public CreateThesisDTO createThesisDTO(Thesis thesis) {
        return mapperUtil.getModelMapper()
                .map(this.thesisRepo
                        .save(mapperUtil.getModelMapper()
                                .map(thesis, Thesis.class)), CreateThesisDTO.class);
    }

    public ThesisDTO getThesis(long id) {
        return this.mapperUtil.getModelMapper()
            .map(this.thesisRepo.findById(id)
                .orElseThrow(() -> new ThesisNotFoundException("Thesis with id=" + id + " not found!")),
                    ThesisDTO.class);
    }

    public List<ThesisDTO> getAllTheses() {
        return this.mapperUtil
                .mapList(
                        this.thesisRepo.findAll(), ThesisDTO.class);
    }

    public void deleteThesis(long id) {
        this.thesisRepo.deleteById(id);
    }

    public ThesisDTO updateThesis(ThesisDTO thesisDTO, long id){
        return this.thesisRepo.findById(id)
                .map(thesis -> {
                    thesis.setTitle(thesisDTO.getTitle() == null ? thesis.getTitle() : thesisDTO.getTitle());
                    thesis.setDateOfSubmission(thesisDTO.getDateOfSubmission() == null ? thesis.getDateOfSubmission() : thesisDTO.getDateOfSubmission());
                    thesis.setText(thesisDTO.getText() == null ? thesis.getText() : thesisDTO.getText());
                    thesis.setApplication(thesisDTO.getApplicationId() == 0 ? thesis.getApplication() : applicationRepo.getById(thesisDTO.getApplicationId()));

                    return this.mapperUtil.getModelMapper()
                        .map(this.thesisRepo.save(thesis), ThesisDTO.class);
                })
                .orElseThrow(() -> new ThesisNotFoundException("Thesis with id=" + id + " not found!"));
    }

    public List<ThesisDTO> getThesesByTitle(String title) {
        return this.mapperUtil
                .mapList(
                        this.thesisRepo.findByTitleContaining(title), ThesisDTO.class);
    }
}
