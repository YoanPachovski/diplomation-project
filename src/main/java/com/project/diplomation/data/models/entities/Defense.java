package com.project.diplomation.data.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "defense")
public class Defense extends BaseEntity {
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "grade")
    @Min(value = 2, message = "Lowest grade is 2!")
    @Max(value = 6, message = "Highest grade is 6!")
    private int grade;

    @OneToOne
    @JoinColumn(name = "review_id", unique = true, nullable = false)
    private Review review;

    public Defense() {}
}
