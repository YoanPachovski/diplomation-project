package com.project.diplomation.data.models.dto;

import com.project.diplomation.data.models.entities.Defense;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateDefenseDTO {
    private LocalDate date;
    private int grade;
    private long reviewId;

    public CreateDefenseDTO mapDefenseToCreateDTO(Defense defense) {
        return new CreateDefenseDTO(
                defense.getDate(),
                defense.getGrade(),
                defense.getReview().getId()
        );
    }
}

