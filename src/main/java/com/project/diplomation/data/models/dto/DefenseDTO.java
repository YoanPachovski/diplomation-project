package com.project.diplomation.data.models.dto;

import com.project.diplomation.data.models.entities.Defense;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DefenseDTO {
    private long id;
    private LocalDate date;
    private int grade;
    private long reviewId;

    public DefenseDTO mapDefenseToDTO(Defense defense) {
        return new DefenseDTO(
                defense.getId(),
                defense.getDate(),
                defense.getGrade(),
                defense.getReview().getId()
        );
    }
}

