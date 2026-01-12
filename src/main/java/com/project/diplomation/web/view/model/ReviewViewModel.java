package com.project.diplomation.web.view.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewViewModel {
    private long id;
    private LocalDate dateOfSubmission;
    private String text;
    private String conclusion;
    private long reviewerId;
    private long thesisId;
    private boolean isPassed;
}

