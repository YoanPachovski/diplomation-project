package com.project.diplomation.data.models.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.diplomation.data.models.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@Table(name = "application")
public class Application extends BaseEntity {
    private String topic;
    private String aims;
    private String problems;
    private String technologies;
    private ApplicationStatus status;
//    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uni_tutor_id", nullable = false)
    private UniversityTutor tutor;
//    @NotNull
    @OneToOne(mappedBy = "application")
    @JsonIgnore
    private Thesis thesis;
    public Application() {}
}
