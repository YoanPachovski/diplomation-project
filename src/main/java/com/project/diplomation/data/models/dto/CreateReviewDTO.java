package com.project.diplomation.data.models.dto;

import com.project.diplomation.data.models.entities.Review;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateReviewDTO {
    private LocalDate dateOfSubmission;
    private String text;
    private String conclusion;
    private long reviewerId;
    private long thesisId;
    private boolean isPassed;

    public CreateReviewDTO mapReviewToCreateDTO (Review review) {
        return new CreateReviewDTO(
                review.getDateOfSubmission(),
                review.getText(),
                review.getConclusion(),
                review.getReviewer().getId(),
                review.getThesis().getId(),
                review.isPassed());
    }
}

