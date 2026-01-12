package com.project.diplomation.web.api;

import com.project.diplomation.data.models.dto.ApplicationDTO;
import com.project.diplomation.data.models.dto.CreateApplicationDTO;
import com.project.diplomation.data.models.entities.Application;
import com.project.diplomation.data.models.entities.Student;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.models.enums.ApplicationStatus;
import com.project.diplomation.data.repositories.StudentRepo;
import com.project.diplomation.data.repositories.UniversityTutorRepo;
import com.project.diplomation.exception.ApplicationNotFoundException;
import com.project.diplomation.service.ApplicationService;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final MapperUtil mapperUtil;
    private final StudentRepo studentRepo;
    private final UniversityTutorRepo tutorRepo;

    @PostMapping("/create")
    public CreateApplicationDTO createApplication(@RequestBody CreateApplicationDTO applicationDTO) {
        Student student = studentRepo.getById(applicationDTO.getStudentId());
        UniversityTutor tutor = tutorRepo.getById(applicationDTO.getTutorId());

        // Create a new Application entity with the fetched foreign keys
        Application application = new Application();
        application.setTopic(applicationDTO.getTopic());
        application.setAims(applicationDTO.getAims());
        application.setProblems(applicationDTO.getProblems());
        application.setTechnologies(applicationDTO.getTechnologies());
        application.setStatus(applicationDTO.getStatus());
        application.setStudent(student);
        application.setTutor(tutor);

        // Save the application in the database
        CreateApplicationDTO savedApplication = applicationService.createApplicationDTO(application);

        // Convert and return response as DTO
        return savedApplication;
    }

    @GetMapping("/{id}")
    public ApplicationDTO getApplication(@PathVariable long id){
        try {
            return this.applicationService.getApplication(id);
        } catch (ApplicationNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application Not Found", exception);
        }
    }

    @GetMapping("/by-student/{studentId}")
    public List<ApplicationDTO> getApplicationsByStudent(@PathVariable long studentId) {
        try {
            return this.applicationService.getApplicationByStudentId(studentId);
        } catch (ApplicationNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application with such studentId Not Found", exception);
        }
    }

    @GetMapping("/all")
    public List<ApplicationDTO> getAllApplications() {
        return this.applicationService.getAllApplications();
    }

    @PutMapping("/update/{id}")
    public ApplicationDTO updateApplication(@PathVariable long id, @RequestBody ApplicationDTO applicationDTO) {
        try {
            return this.applicationService.updateApplication(applicationDTO, id);
        } catch (ApplicationNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application to be updated Not Found", exception);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteApplication(@PathVariable long id) {
        try {
            this.applicationService.deleteApplication(id);
        } catch (ApplicationNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Application to be deleted Not Found", exception);
        }
    }

    @GetMapping("/by-status/{status}")
    public List<ApplicationDTO> getApplicationsByStatus(@PathVariable ApplicationStatus status) {
        return this.applicationService.getApplicationsByStatus(status);
    }

    @GetMapping("/by-tutor/{tutorId}")
    public List<ApplicationDTO> getApplicationsByTutor(@PathVariable long tutorId) {
        return this.applicationService.getApplicationsByTutor(tutorId);
    }
}
