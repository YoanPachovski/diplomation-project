package com.project.diplomation.data.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.diplomation.data.models.enums.PositionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Represents a university tutor entity.
 * Has an id, name and position type.
 */
@Builder
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name="uni_tutor")
public class UniversityTutor extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "position_type")
    private PositionType positionType;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews;
    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Application> applications;
}
