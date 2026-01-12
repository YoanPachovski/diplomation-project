package com.project.diplomation.data.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

/**
 * Represents a student entity.
 * Has an id, name and faculty number.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "student")
public class Student extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "f_number", unique = true)
    @Pattern(regexp = "[0-9]{5,6}", message = "Faculty numbers should consist of 5 or 6 consecutive numbers!")
    private String fNumber;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Application> applications;
}
