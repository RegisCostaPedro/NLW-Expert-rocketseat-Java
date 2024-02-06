package com.rocketseat.certification_nlw.modules.students.entities;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationStudentEntity {
    private UUID id;
    private UUID studentID;
    private String technology;
    private int grate;
    private List<AnswersCertificationsEntity> answersCertificationsEntities;


}
