package com.project.diplomation.web.view.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

/**
 * Represents the data of a student.
 * Has a name and faculty number.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateStudentViewModel {
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp = "[0-9]{5,6}", message = "Faculty numbers should consist of 5 or 6 consecutive numbers!")
    private String fNumber;
}

