package com.project.diplomation.service;

import com.project.diplomation.data.models.dto.ApplicationDTO;
import com.project.diplomation.data.models.dto.CreateApplicationDTO;
import com.project.diplomation.data.models.entities.Application;
import com.project.diplomation.data.models.enums.ApplicationStatus;
import com.project.diplomation.data.repositories.ApplicationRepo;
import com.project.diplomation.data.repositories.StudentRepo;
import com.project.diplomation.data.repositories.UniversityTutorRepo;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepo applicationRepo;
    private final MapperUtil mapperUtil;
    private final StudentRepo studentRepo;
    private final UniversityTutorRepo tutorRepo;

    public CreateApplicationDTO createApplicationDTO(Application application) {
        return mapperUtil.getModelMapper()
                .map(this.applicationRepo
                        .save(mapperUtil.getModelMapper()
                                .map(application, Application.class)), CreateApplicationDTO.class);

    }

    public ApplicationDTO getApplication(long id) {
        return this.mapperUtil.getModelMapper()
            .map(this.applicationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application with id=" + id + " not found!")),
                    ApplicationDTO.class);
    }

    public List<ApplicationDTO> getApplicationByStudentId(long studentId) {
        return this.mapperUtil
                .mapList(
                        this.applicationRepo.findByStudentId(studentId), ApplicationDTO.class);
    }

    public List<ApplicationDTO> getAllApplications() {
        return this.mapperUtil
                .mapList(
                        this.applicationRepo.findAll(), ApplicationDTO.class);
    }

    public ApplicationDTO updateApplication(ApplicationDTO applicationDTO, long id) {
//        applicationDTO == json object
//        application == entity from db
        return this.applicationRepo.findById(id)
                .map(application -> {
                    application.setTopic(applicationDTO.getTopic() == null ? application.getTopic() : applicationDTO.getTopic());
                    application.setAims(applicationDTO.getAims() == null ? application.getAims() : applicationDTO.getAims());
                    application.setProblems(applicationDTO.getProblems() == null ? application.getProblems() : applicationDTO.getProblems());
                    application.setTechnologies(applicationDTO.getTechnologies() == null ? application.getTechnologies() : applicationDTO.getTechnologies());
                    application.setStatus(applicationDTO.getStatus() == null ? application.getStatus() : applicationDTO.getStatus());
                    application.setStudent(applicationDTO.getStudentId() == 0 ? application.getStudent() : studentRepo.getById(applicationDTO.getStudentId()));
                    application.setTutor(applicationDTO.getTutorId() == 0 ? application.getTutor() : tutorRepo.getById(applicationDTO.getTutorId()));
                    return mapperUtil.getModelMapper()
                            .map(this.applicationRepo.save(application), ApplicationDTO.class);
                })
                .orElseThrow(() -> new RuntimeException("Application with id=" + id + " not found!"));
    }

    public void deleteApplication(long id) {
        try {
            this.applicationRepo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Application with id=" + id + " could not be deleted!");
        }
    }

    public List<ApplicationDTO> getApplicationsByStatus(ApplicationStatus status) {
        return this.mapperUtil
                .mapList(
                        this.applicationRepo.findByStatus(status), ApplicationDTO.class);
    }

    public List<ApplicationDTO> getApplicationsByTutor(long tutorId) {
        return this.mapperUtil
                .mapList(
                        this.applicationRepo.findByTutor(tutorRepo.getById(tutorId)), ApplicationDTO.class);
    }
}
