package com.project.diplomation.web.api;

import com.project.diplomation.data.models.dto.CreateReviewDTO;
import com.project.diplomation.data.models.dto.ReviewDTO;
import com.project.diplomation.data.models.dto.ThesisDTO;
import com.project.diplomation.data.models.entities.Review;
import com.project.diplomation.data.models.entities.Thesis;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.repositories.ThesisRepo;
import com.project.diplomation.data.repositories.UniversityTutorRepo;
import com.project.diplomation.exception.ReviewNotFoundException;
import com.project.diplomation.exception.ThesisNotFoundException;
import com.project.diplomation.service.ReviewService;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final MapperUtil mapperUtil;
    private final ThesisRepo thesisRepo;
    private final UniversityTutorRepo universityTutorRepo;

    @PostMapping("/create")
    public CreateReviewDTO createReview(@RequestBody CreateReviewDTO reviewDTO) {
        Thesis thesis = thesisRepo.getById(reviewDTO.getThesisId());
        UniversityTutor reviewer = universityTutorRepo.getById(reviewDTO.getReviewerId());
//        save all data in review entity
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setThesis(thesis);
        review.setConclusion(reviewDTO.getConclusion());
        review.setText(reviewDTO.getText());
        review.setDateOfSubmission(reviewDTO.getDateOfSubmission() == null ? LocalDate.now() : reviewDTO.getDateOfSubmission());
        review.setPassed(reviewDTO.isPassed());

        return reviewService.createReviewDTO(review);
    }

    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable long id){
        try {
            return this.reviewService.getReview(id);
        } catch (ReviewNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review Not Found", exception);
        }
    }

    @GetMapping("/all")
    public List<ReviewDTO> getAllReviews() {
        return this.reviewService.getAllReviews();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReview(@PathVariable long id) {
        try {
            this.reviewService.deleteReview(id);
        } catch (ReviewNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Review to be deleted Not Found", exception);
        }
    }

    @GetMapping("/by-thesis-id/{id}")
    public ReviewDTO getReviewByThesisId(@PathVariable long id) {
        return this.reviewService.getReviewByThesisId(id);
    }

    @PutMapping("/update/{id}")
    public ReviewDTO updateReview(@PathVariable long id, @RequestBody ReviewDTO reviewDTO) {
        try {
            return this.reviewService.updateReview(reviewDTO, id);
        } catch (ReviewNotFoundException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Thesis Not Found", exception);
        }
    }

    @GetMapping("/count-failed-students")
    public Long countUniqueStudentsWithFailedReviews() {
        return reviewService.countUniqueStudentsWithFailedReviews();
    }

    @GetMapping("/by-reviewer-id/{id}")
    public List<ReviewDTO> getReviewByReviewerId(@PathVariable long id) {
        return this.reviewService.getReviewsByReviewerId(id);
    }


}
