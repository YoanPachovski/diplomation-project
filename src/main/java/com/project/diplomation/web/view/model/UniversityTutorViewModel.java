package com.project.diplomation.web.view.model;

import com.project.diplomation.data.models.enums.PositionType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UniversityTutorViewModel {
    private long id;
    private String name;
    private PositionType positionType;
}
