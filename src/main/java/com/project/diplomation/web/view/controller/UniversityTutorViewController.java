package com.project.diplomation.web.view.controller;

import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.models.enums.PositionType;
import com.project.diplomation.service.UniversityTutorService;
import com.project.diplomation.util.MapperUtil;
import com.project.diplomation.web.view.model.CreateUniversityTutorViewModel;
import com.project.diplomation.web.view.model.UniversityTutorViewModel;
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
@RequestMapping("/uniTutors")
public class UniversityTutorViewController {
    private final MapperUtil mapperUtil;
    private final UniversityTutorService universityTutorService;

    @GetMapping("/view/{id}")
    public String getUniTutorView(Model model, @PathVariable long id) {
        UniversityTutorViewModel tutor = mapperUtil
                .getModelMapper()
                .map(this.universityTutorService.getUniversityTutor(id), UniversityTutorViewModel.class);
        model.addAttribute("uniTutor", tutor);
        return "uniTutors/view";
    }
    @GetMapping
    String getUniTutorsView(Model model) {
        List<UniversityTutorViewModel> tutors = mapperUtil
                .mapList(this.universityTutorService.getAllUniversityTutors(), UniversityTutorViewModel.class);
        model.addAttribute("uniTutors", tutors);
        // returns the name of the template page(view) to be rendered
        return "uniTutors/uniTutors.html";
    }
    @GetMapping("/delete/{id}")
    public String deleteUniTutor(@PathVariable long id) {
        this.universityTutorService.deleteUniTutor(id);
        return "redirect:/uniTutors";
    }
    @GetMapping("/edit/{id}")
    public String showEditUniTutorForm(Model model, @PathVariable Long id) {
        model.addAttribute("uniTutor", this.universityTutorService.getUniversityTutor(id));
        List<PositionType> positionTypes = Arrays.asList(PositionType.values());
        model.addAttribute("positionTypes", positionTypes);
        return "/uniTutors/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUniTutor(@PathVariable long id, UniversityTutor tutor) {
        this.universityTutorService.updateUniTutor(tutor, id);
        return "redirect:/uniTutors";
    }
    @GetMapping("/create")
    public String showCreateUniTutorForm(Model model) {
        model.addAttribute("uniTutor", new UniversityTutor());
        List<PositionType> positionTypes = Arrays.asList(PositionType.values());
        model.addAttribute("positionTypes", positionTypes);
        return "/uniTutors/create";
    }
    @PostMapping("/save")
    public String createUniTutor(@Valid @ModelAttribute("uniTutor") CreateUniversityTutorViewModel uniTutor, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/uniTutors/create";
        }
        this.universityTutorService
            .createUniversityTutorDTO(mapperUtil.getModelMapper().map(uniTutor, UniversityTutor.class));
        return "redirect:/uniTutors";
    }
}
