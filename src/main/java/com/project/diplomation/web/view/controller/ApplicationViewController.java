package com.project.diplomation.web.view.controller;

import com.project.diplomation.data.models.dto.ApplicationDTO;
import com.project.diplomation.data.models.dto.CreateApplicationDTO;
import com.project.diplomation.data.models.dto.UniversityTutorDTO;
import com.project.diplomation.data.models.entities.Application;
import com.project.diplomation.data.models.entities.Student;
import com.project.diplomation.data.models.dto.StudentDTO;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.models.enums.ApplicationStatus;
import com.project.diplomation.data.repositories.StudentRepo;
import com.project.diplomation.data.repositories.UniversityTutorRepo;
import com.project.diplomation.service.ApplicationService;
import com.project.diplomation.service.StudentService;
import com.project.diplomation.service.UniversityTutorService;
import com.project.diplomation.util.MapperUtil;
import com.project.diplomation.web.view.model.ApplicationViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationViewController {
    private final MapperUtil mapperUtil;
    private final ApplicationService applicationService;
    private final StudentService studentService;
    private final UniversityTutorService universityTutorService;
    private final StudentRepo studentRepo;
    private final UniversityTutorRepo tutorRepo;

    @GetMapping("/view/{id}")
    public String getApplicationView(Model model, @PathVariable long id) {
        ApplicationViewModel application = mapperUtil
                .getModelMapper()
                .map(this.applicationService.getApplication(id), ApplicationViewModel.class);
        // !!! application is a keyword in Thymeleaf
        model.addAttribute("appl", application);

        return "applications/view";
    }

    @GetMapping
    String getApplicationsView(Model model) {
        List<ApplicationViewModel> applications = mapperUtil
                .mapList(this.applicationService.getAllApplications(), ApplicationViewModel.class);
        model.addAttribute("applications", applications);

        List<ApplicationStatus> statuses = Arrays.asList(ApplicationStatus.values());
        model.addAttribute("statuses", statuses);

        List<Long> tutorIds = applicationService.getAllApplications()
                .stream()
                .map(ApplicationDTO::getTutorId)
                .distinct()
                .sorted()
                .toList();
        model.addAttribute("tutorIds", tutorIds);
        // returns the name of the template page(view) to be rendered
        return "applications/applications";
    }
    @GetMapping("/delete/{id}")
    public String deleteApplication(@PathVariable long id) {
        this.applicationService.deleteApplication(id);
        return "redirect:/applications";
    }

    @GetMapping("/edit/{id}")
    public String showEditApplicationForm(Model model, @PathVariable Long id) {
        model.addAttribute("appl", this.applicationService.getApplication(id));
        List<ApplicationStatus> statuses = Arrays.asList(ApplicationStatus.values());
        model.addAttribute("statuses", statuses);
        List<Long> studentIds = studentService.getAllStudents()
                .stream()
                .map(StudentDTO::getId)
                .distinct()
                .toList();
        model.addAttribute("studentIds", studentIds);

        List<Long> tutorIds = universityTutorService.getAllUniversityTutors()
                .stream()
                .map(UniversityTutorDTO::getId)
                .toList();
        model.addAttribute("tutorIds", tutorIds);
        return "/applications/edit";
    }

    @PostMapping("/update/{id}")
    public String updateApplication(@PathVariable long id, ApplicationDTO applicationDTO) {
        this.applicationService.updateApplication(applicationDTO, id);
        return "redirect:/applications";
    }
    @GetMapping("/create")
    public String showCreateApplicationForm(Model model) {
        model.addAttribute("appl", new CreateApplicationDTO());
        List<ApplicationStatus> statuses = Arrays.asList(ApplicationStatus.values());
        model.addAttribute("statuses", statuses);

        List<Long> studentIds = studentService.getAllStudents()
                .stream()
                .map(StudentDTO::getId)
                .toList();
        model.addAttribute("studentIds", studentIds);

        List<Long> tutorIds = universityTutorService.getAllUniversityTutors()
                .stream()
                .map(UniversityTutorDTO::getId)
                .toList();
        model.addAttribute("tutorIds", tutorIds);

        return "/applications/create";
    }

    @PostMapping("/save")
    public String createApplication(@Valid @ModelAttribute("appl") CreateApplicationDTO appl, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/applications/create";
        }

        Student student = studentRepo.getById(appl.getStudentId());
        UniversityTutor tutor = tutorRepo.getById(appl.getTutorId());

        Application application = new Application();
        application.setTopic(appl.getTopic());
        application.setAims(appl.getAims());
        application.setProblems(appl.getProblems());
        application.setTechnologies(appl.getTechnologies());
        application.setStatus(appl.getStatus());
        application.setStudent(student);
        application.setTutor(tutor);

        this.applicationService
                .createApplicationDTO(application);
        return "redirect:/applications";
    }
    @GetMapping("/by-status")
    String filterApplicationsByStatus(@RequestParam("status") ApplicationStatus status, Model model) {
        List<ApplicationViewModel> filteredApplications = mapperUtil
                .mapList(this.applicationService.getApplicationsByStatus(status), ApplicationViewModel.class);
        model.addAttribute("applications", filteredApplications);

        List<ApplicationStatus> statuses = Arrays.asList(ApplicationStatus.values());
        model.addAttribute("statuses", statuses);

        List<Long> tutorIds = applicationService.getAllApplications()
                .stream()
                .map(ApplicationDTO::getTutorId)
                .distinct()
                .toList();
        model.addAttribute("tutorIds", tutorIds);

        return "applications/applications";
    }

    @GetMapping("/by-tutor")
    String filterApplicationsByTutor(@RequestParam("tutorId") long tutorId, Model model) {
        List<ApplicationViewModel> filteredApplications = mapperUtil
                .mapList(this.applicationService.getApplicationsByTutor(tutorId), ApplicationViewModel.class);
        model.addAttribute("applications", filteredApplications);

        List<ApplicationStatus> statuses = Arrays.asList(ApplicationStatus.values());
        model.addAttribute("statuses", statuses);

        List<Long> tutorIds = applicationService.getAllApplications()
                .stream()
                .map(ApplicationDTO::getTutorId)
                .distinct()
                .toList();

        model.addAttribute("tutorIds", tutorIds);

        return "applications/applications";
    }
}
