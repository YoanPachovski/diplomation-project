package com.project.diplomation.web.view.controller;

import com.project.diplomation.data.models.dto.*;
import com.project.diplomation.data.models.entities.Application;
import com.project.diplomation.data.models.entities.Review;
import com.project.diplomation.data.models.entities.Thesis;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.models.enums.ApplicationStatus;
import com.project.diplomation.data.repositories.ThesisRepo;
import com.project.diplomation.data.repositories.UniversityTutorRepo;
import com.project.diplomation.service.ReviewService;
import com.project.diplomation.service.ThesisService;
import com.project.diplomation.service.UniversityTutorService;
import com.project.diplomation.util.MapperUtil;
import com.project.diplomation.web.view.model.ApplicationViewModel;
import com.project.diplomation.web.view.model.ReviewViewModel;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Data
@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewViewController {
    private final MapperUtil mapperUtil;
    private final ReviewService reviewService;
    private final UniversityTutorService universityTutorService;
    private final UniversityTutorRepo universityTutorRepo;
    private final ThesisService thesisService;
    private final ThesisRepo thesisRepo;

    @GetMapping("/view/{id}")
    public String getReviewView(Model model, @PathVariable long id) {
        ReviewViewModel review = mapperUtil
                .getModelMapper()
                .map(this.reviewService.getReview(id), ReviewViewModel.class);
        model.addAttribute("review", review);

        return "reviews/view";
    }

    @GetMapping
    String getReviewsView(Model model) {
        List<ReviewViewModel> reviews = mapperUtil
                .mapList(this.reviewService.getAllReviews(), ReviewViewModel.class);
        model.addAttribute("reviews", reviews);

        List<Long> reviewerIds = reviewService.getAllReviews()
                .stream()
                .map(ReviewDTO::getReviewerId)
                .distinct()
                .toList();
        model.addAttribute("reviewerIds", reviewerIds);

//        List<Long> thesisIds = thesisService.getAllTheses()
//                .stream()
//                .map(ThesisDTO::getId)
//                .toList();
//        model.addAttribute("thesisIds", thesisIds);
        return "reviews/reviews";
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable long id) {
        this.reviewService.deleteReview(id);
        return "redirect:/reviews";
    }

    @GetMapping("/create")
    public String showCreateReviewForm(Model model) {
        model.addAttribute("review", new CreateReviewDTO());

        List<Long> reviewerIds = universityTutorService.getAllUniversityTutors()
                .stream()
                .map(UniversityTutorDTO::getId)
                .toList();
        model.addAttribute("reviewerIds", reviewerIds);

        List<Long> thesisIds = thesisService.getAllTheses()
                .stream()
                .map(ThesisDTO::getId)
                .toList();
        model.addAttribute("thesisIds", thesisIds);

        return "/reviews/create";
    }

    @PostMapping("/save")
    public String createReview(@Valid @ModelAttribute("review") CreateReviewDTO reviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/reviews/create";
        }

        // foreign keys
        UniversityTutor reviewer = universityTutorRepo.getReferenceById(reviewDTO.getReviewerId());
        Thesis thesis = thesisRepo.getReferenceById(reviewDTO.getThesisId());

        //create the entity
        Review review = new Review(
                reviewDTO.getDateOfSubmission() == null ? LocalDate.now() : reviewDTO.getDateOfSubmission(),
                reviewDTO.getText(),
                reviewDTO.getConclusion(),
                reviewer,
                thesis,
                reviewDTO.isPassed()
        );

        this.reviewService
                .createReviewDTO(review);
        return "redirect:/reviews";
    }

    @GetMapping("/edit/{id}")
    public String showEditReviewForm(Model model, @PathVariable Long id) {
        model.addAttribute("review", this.reviewService.getReview(id));

        List<Long> reviewerIds = universityTutorService.getAllUniversityTutors()
                .stream()
                .map(UniversityTutorDTO::getId)
                .toList();
        model.addAttribute("reviewerIds", reviewerIds);

        List<Long> thesisIds = thesisService.getAllTheses()
                .stream()
                .map(ThesisDTO::getId)
                .toList();
        model.addAttribute("thesisIds", thesisIds);

        return "/reviews/edit";
    }

    @PostMapping("/update/{id}")
    public String updateReview(@PathVariable long id, ReviewDTO reviewDTO) {
        this.reviewService.updateReview(reviewDTO, id);
        return "redirect:/reviews";
    }

    @GetMapping("/by-reviewer")
    String filterReviewsByReviewer(@RequestParam("reviewerId") long reviewerId, Model model) {
        List<ReviewViewModel> filteredReviews = mapperUtil
                .mapList(this.reviewService.getReviewsByReviewerId(reviewerId), ReviewViewModel.class);
        model.addAttribute("reviews", filteredReviews);

        List<Long> reviewerIds = reviewService.getAllReviews()
                .stream()
                .map(ReviewDTO::getReviewerId)
                .distinct()
                .toList();
        model.addAttribute("reviewerIds", reviewerIds);

        return "reviews/reviews";
    }
}
