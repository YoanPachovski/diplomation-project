package com.project.diplomation.data.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "thesis")
public class Thesis extends BaseEntity {
    @Column(name = "title")
    private String title;
    @Column(name = "text", columnDefinition = "LONGTEXT")
    private String text;
    @Column(name = "date_of_submission")
    private LocalDate dateOfSubmission;
    @OneToOne(cascade = CascadeType.ALL)
    private Application application;

    @OneToOne(mappedBy = "thesis")
    @JsonIgnore
    private Review review;

    public Thesis() {}
}
