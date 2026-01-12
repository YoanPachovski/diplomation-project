package com.project.diplomation.service;

import com.project.diplomation.data.models.dto.ApplicationDTO;
import com.project.diplomation.data.models.dto.CreateReviewDTO;
import com.project.diplomation.data.models.dto.ReviewDTO;
import com.project.diplomation.data.models.dto.ThesisDTO;
import com.project.diplomation.data.models.entities.Review;
import com.project.diplomation.data.models.entities.Thesis;
import com.project.diplomation.data.models.entities.UniversityTutor;
import com.project.diplomation.data.repositories.ReviewRepo;
import com.project.diplomation.data.repositories.ThesisRepo;
import com.project.diplomation.data.repositories.UniversityTutorRepo;
import com.project.diplomation.exception.ReviewNotFoundException;
import com.project.diplomation.exception.ThesisNotFoundException;
import com.project.diplomation.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final ThesisRepo thesisRepo;
    private final UniversityTutorRepo universityTutorRepo;
    private final MapperUtil mapperUtil;
    private final UniversityTutorRepo tutorRepo;

    public CreateReviewDTO createReviewDTO(Review review) {
        reviewRepo.save(review);

        CreateReviewDTO createReviewDTO = new CreateReviewDTO();
        return createReviewDTO.mapReviewToCreateDTO(review);
    }

    public ReviewDTO getReview(long id) {
        Optional<Review> review = reviewRepo.findById(id);
        return new ReviewDTO(
                review.get().getId(),
                review.get().getDateOfSubmission(),
                review.get().getText(),
                review.get().getConclusion(),
                review.get().getReviewer().getId(),
                review.get().getThesis().getId(),
                review.get().isPassed());
    }

    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = this.reviewRepo.findAll();
        ReviewDTO reviewDTO = new ReviewDTO();
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (Review review : reviews) {
            reviewDTOS.add(reviewDTO.mapReviewToDTO(review));
        }
        return reviewDTOS;
    }

    public void deleteReview(long id) {
        this.reviewRepo.deleteById(id);
    }

    public ReviewDTO getReviewByThesisId(long id) {
        return this.mapperUtil.getModelMapper()
                .map(this.reviewRepo.findReviewByThesisId(id), ReviewDTO.class);
    }

    public ReviewDTO updateReview(ReviewDTO reviewDTO, long id){
        return this.reviewRepo.findById(id)
                .map(review -> {
                    review.setText(reviewDTO.getText() == null ? review.getText() : reviewDTO.getText());
                    review.setDateOfSubmission(reviewDTO.getDateOfSubmission() == null ? review.getDateOfSubmission() : reviewDTO.getDateOfSubmission());
                    review.setText(reviewDTO.getText() == null ? review.getText() : reviewDTO.getText());
                    review.setConclusion(reviewDTO.getConclusion() == null ? review.getConclusion() : reviewDTO.getConclusion());
                    review.setPassed(reviewDTO.isPassed());

                    review.setReviewer(reviewDTO.getReviewerId() == 0 ? review.getReviewer() : universityTutorRepo.getReferenceById(reviewDTO.getReviewerId()));
                    review.setThesis(reviewDTO.getThesisId() == 0 ? review.getThesis() : thesisRepo.getReferenceById(reviewDTO.getThesisId()));

                    reviewRepo.save(review);

                    ReviewDTO newReviewDTO = new ReviewDTO();
                    return newReviewDTO.mapReviewToDTO(review);
                })
                .orElseThrow(() -> new ReviewNotFoundException("Review with id=" + id + " not found!"));
    }

    public Long countUniqueStudentsWithFailedReviews() {
        return reviewRepo.countUniqueStudentsWithFailedReviews();
    }

    public List<ReviewDTO> getReviewsByReviewerId(long reviewerId) {
        return this.mapperUtil
                .mapList(
                        this.reviewRepo.findByReviewerId(reviewerId), ReviewDTO.class);


    }
}
