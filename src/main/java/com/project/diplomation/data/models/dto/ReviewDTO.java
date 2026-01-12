package com.project.diplomation.data.models.dto;

import com.project.diplomation.data.models.entities.Review;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDTO {
    private long id;
    private LocalDate dateOfSubmission;
    private String text;
    private String conclusion;
    private long reviewerId;
    private long thesisId;
    private boolean isPassed;

    public ReviewDTO mapReviewToDTO (Review review) {
        ReviewDTO reviewDTO = new ReviewDTO(
                review.getId(),
                review.getDateOfSubmission(),
                review.getText(),
                review.getConclusion(),
                review.getReviewer().getId(),
                review.getThesis().getId(),
                review.isPassed());
        return reviewDTO;
    }

}

