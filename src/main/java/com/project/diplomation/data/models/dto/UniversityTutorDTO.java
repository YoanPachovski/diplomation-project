package com.project.diplomation.data.models.dto;

import com.project.diplomation.data.models.enums.PositionType;
import lombok.*;

/**
 * Represents a placeholder for the data of a university tutor.
 * Has an id, name and position type.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UniversityTutorDTO {
    private long id;
    private String name;
    private PositionType positionType;
}
