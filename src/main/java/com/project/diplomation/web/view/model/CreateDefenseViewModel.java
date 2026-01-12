package com.project.diplomation.web.view.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateDefenseViewModel {
    private LocalDate date;
    private int grade;
    private long reviewId;
}

