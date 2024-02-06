package com.rocketseat.certification_nlw.modules.students.entities;
import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {
    
    private UUID id;
    private String email;
    private List<CertificationStudentEntity> certificationStudentEntity;
}
