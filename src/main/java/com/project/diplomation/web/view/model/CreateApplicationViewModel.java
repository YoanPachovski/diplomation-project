package com.project.diplomation.web.view.model;

import com.project.diplomation.data.models.enums.ApplicationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateApplicationViewModel {
    private String topic;
    private String aims;
    private String problems;
    private String technologies;
    private ApplicationStatus status;
    private long studentId;
    private long tutorId;
}
