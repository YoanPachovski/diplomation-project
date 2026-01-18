package com.project.diplomation.data.models.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

/**
 * Represents a placeholder for the data of a student.
 * Has an id, name and faculty number.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class StudentDTO {
    private long id;
    private String name;
    @UniqueElements
    @Pattern(regexp = "[0-9]{5,6}", message = "Faculty numbers should consist of 5 or 6 consecutive numbers!")
    private String fNumber;

}

