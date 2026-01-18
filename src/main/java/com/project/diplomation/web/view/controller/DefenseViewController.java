package com.project.diplomation.web.view.controller;

import com.project.diplomation.data.models.dto.*;
import com.project.diplomation.data.models.entities.Defense;
import com.project.diplomation.data.models.entities.Review;
import com.project.diplomation.data.models.entities.Thesis;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.repositories.ReviewRepo;
import com.project.diplomation.service.DefenseService;
import com.project.diplomation.service.ReviewService;
import com.project.diplomation.util.MapperUtil;
import com.project.diplomation.web.view.model.DefenseViewModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Controller
@RequiredArgsConstructor
@RequestMapping("/defenses")
public class DefenseViewController {
    private final MapperUtil mapperUtil;
    private final DefenseService defenseService;
    private final ReviewRepo reviewRepo;
    private final ReviewService reviewService;

    @GetMapping("/view/{id}")
    public String getDefenseView(Model model, @PathVariable long id) {
        DefenseViewModel defense = mapperUtil
                .getModelMapper()
                .map(this.defenseService.getDefense(id), DefenseViewModel.class);
        model.addAttribute("defense", defense);

        return "defenses/view";
    }

    @GetMapping
    String getDefensesView(Model model) throws NoSuchFieldException {
        List<DefenseViewModel> defenses = mapperUtil
                .mapList(this.defenseService.getAllDefenses(), DefenseViewModel.class);
        model.addAttribute("defenses", defenses);

        return "defenses/defenses";
    }

    @GetMapping("/delete/{id}")
    public String deleteDefense(@PathVariable long id) {
        this.defenseService.deleteDefense(id);
        return "redirect:/defenses";
    }

    @GetMapping("/create")
    public String showCreateDefenseForm(Model model) {
        model.addAttribute("defense", new CreateDefenseDTO());

        List<Long> reviewIds = reviewService.getReviewsByIsPassed()
                .stream()
                .map(ReviewDTO::getId)
                .toList();
        model.addAttribute("reviewIds", reviewIds);

        return "/defenses/create";
    }

    @PostMapping("/save")
    public String createDefense(@Valid @ModelAttribute("defense") CreateDefenseDTO defenseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/defenses/create";
        }

        // foreign keys
        Review review = reviewRepo.getReferenceById(defenseDTO.getReviewId());

        //create the entity
        Defense defense = new Defense(
                defenseDTO.getDate() == null ? LocalDate.now() : defenseDTO.getDate(),
                defenseDTO.getGrade(),
                review
        );

        this.defenseService
                .createDefenseDTO(defense);
        return "redirect:/defenses";
    }

    @GetMapping("/edit/{id}")
    public String showEditDefenseForm(Model model, @PathVariable Long id) {
        model.addAttribute("defense", this.defenseService.getDefense(id));

        List<Long> reviewIds = reviewService.getAllReviews()
                .stream()
                .map(ReviewDTO::getId)
                .toList();
        model.addAttribute("reviewIds", reviewIds);

        return "/defenses/edit";
    }

    @PostMapping("/update/{id}")
    public String updateDefense(@PathVariable long id, DefenseDTO defenseDTO) {
        this.defenseService.updateDefense(defenseDTO, id);
        return "redirect:/defenses";
    }

    @GetMapping("/by-grade")
    public String getDefensesByGradeView(Model model, @RequestParam int grade) throws NoSuchFieldException {
        List<DefenseViewModel> defenses = mapperUtil
                .mapList(this.defenseService.getDefensesByGrade(grade), DefenseViewModel.class);
        model.addAttribute("defenses", defenses);

        return "defenses/defenses";
    }
}
