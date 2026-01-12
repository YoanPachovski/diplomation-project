package com.project.diplomation.web.view.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ThesisViewModel {
    private Long id;
    private String title;
    private String text;
    private LocalDate dateOfSubmission;
    private long applicationId;
}
