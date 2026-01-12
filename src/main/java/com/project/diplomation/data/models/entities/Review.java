package com.project.diplomation.data.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "review")
public class Review extends BaseEntity {
    @Column(name = "date_of_submission")
    private LocalDate dateOfSubmission;
    @Column(name = "text", columnDefinition = "TEXT")
    private String text;
    @Column(name = "conclusion", columnDefinition = "TEXT")
    private String conclusion;
    @ManyToOne
    @JoinColumn(name = "reviewer_id", nullable = false, columnDefinition = "BIGINT")
    private UniversityTutor reviewer;
    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "thesis_id", nullable = false, columnDefinition = "BIGINT")
    private Thesis thesis;
    @Column(name = "is_passed")
    private boolean isPassed;
    public Review() {}
}
