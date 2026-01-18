package com.project.diplomation.web.view.controller;

import com.project.diplomation.data.models.dto.*;
import com.project.diplomation.data.models.entities.*;
import com.project.diplomation.data.models.enums.ApplicationStatus;
import com.project.diplomation.data.repositories.ApplicationRepo;
import com.project.diplomation.service.ApplicationService;
import com.project.diplomation.service.ThesisService;
import com.project.diplomation.util.MapperUtil;
import com.project.diplomation.web.view.model.ThesisViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/theses")
public class ThesisViewController {
    private final MapperUtil mapperUtil;
    private final ThesisService thesisService;
    private final ApplicationService applicationService;
    private final ApplicationRepo applicationRepo;

    @GetMapping("/view/{id}")
    public String getThesisView(Model model, @PathVariable long id) {
        ThesisViewModel thesis = mapperUtil
                .getModelMapper()
                .map(this.thesisService.getThesis(id), ThesisViewModel.class);
        model.addAttribute("thesis", thesis);

        return "theses/view";
    }

    @GetMapping
    String getThesesView(Model model) {
        List<ThesisViewModel> theses = mapperUtil
                .mapList(this.thesisService.getAllTheses(), ThesisViewModel.class);
        model.addAttribute("theses", theses);
        return "theses/theses";
    }
    @GetMapping("/delete/{id}")
    public String deleteThesis(@PathVariable long id) {
        this.thesisService.deleteThesis(id);
        return "redirect:/theses";
    }

    @GetMapping("/edit/{id}")
    public String showEditThesisForm(Model model, @PathVariable Long id) {
        model.addAttribute("thesis", this.thesisService.getThesis(id));

        List<Long> applicationIds = applicationService.getAllApplications()
                .stream()
                .map(ApplicationDTO::getId)
                .toList();
        model.addAttribute("applicationIds", applicationIds);
        return "/theses/edit";
    }

    @PostMapping("/update/{id}")
    public String updateThesis(@PathVariable long id, ThesisDTO thesisDTO) {
        this.thesisService.updateThesis(thesisDTO, id);
        return "redirect:/theses";
    }
    @GetMapping("/create")
    public String showCreateApplicationForm(Model model) {
        model.addAttribute("thesis", new CreateThesisDTO());

        List<Long> applicationIds = applicationService.getApplicationsByStatus(ApplicationStatus.ACCEPTED)
                .stream()
                .map(ApplicationDTO::getId)
                .toList();
        model.addAttribute("applicationIds", applicationIds);

        return "/theses/create";
    }

    @PostMapping("/save")
    public String createThesis(@Valid @ModelAttribute("thesis") CreateThesisDTO thesisDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/theses/create";
        }

        Application application = applicationRepo.getById(thesisDTO.getApplicationId());

        Thesis thesis = new Thesis();
        thesis.setTitle(thesisDTO.getTitle());
        thesis.setText(thesisDTO.getText());
        thesis.setDateOfSubmission(thesisDTO.getDateOfSubmission() == null ? LocalDate.now() : thesisDTO.getDateOfSubmission());
        thesis.setApplication(application);

        this.thesisService
                .createThesisDTO(thesis);
        return "redirect:/theses";
    }

    @GetMapping("/by-title")
    public String getThesesByTitle(@RequestParam("title") String title, Model model) {
        List<ThesisViewModel> theses = mapperUtil
                .mapList(this.thesisService.getThesesByTitle(title), ThesisViewModel.class);
        model.addAttribute("theses", theses);
        return "theses/theses";
    }
}
