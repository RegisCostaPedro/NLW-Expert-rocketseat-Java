package com.rocketseat.certification_nlw.modules.questions.entitiesQuestions;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "alternatives")
public class AlternativeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

   /*  @OneToMany
    @JoinColumn(name = "question_id", nullable = false, updatable = false)
    private QuestionEntity questionId;*/

    private boolean isCorrect;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
