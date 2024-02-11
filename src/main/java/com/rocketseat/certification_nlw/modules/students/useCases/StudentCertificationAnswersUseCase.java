package com.rocketseat.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.questions.entitiesQuestions.QuestionEntity;
import com.rocketseat.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.rocketseat.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCeriticationDTO;
import com.rocketseat.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.rocketseat.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.rocketseat.certification_nlw.modules.students.entities.StudentEntity;
import com.rocketseat.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.rocketseat.certification_nlw.modules.students.repositories.StudentRepository;



@Service
public class StudentCertificationAnswersUseCase {


    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository   studentRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificataionUseCase verifyIfHasCertificataionUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

      var hasCertification = this.verifyIfHasCertificataionUseCase.execute(new VerifyHasCeriticationDTO(dto.getEmail(),dto.getTechnology()));
       
      if (hasCertification) {
        throw new Exception("You've already gotten your certification!");
      }
        // Verificar se o usuário existe

        // Buscar as alternativas das perguntas
        // Correta ou incorreta

        AtomicInteger  correctAnswers = new AtomicInteger(0);

        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();
        dto.getQuestionAnswers()
            .forEach(questionAnswer -> {
                var optionalQuestion = questionsEntity.stream()
                        .filter(q -> q.getId().equals(questionAnswer.getQuestionID()))
                        .findFirst();
        
                if (optionalQuestion.isPresent()) {
                    var question = optionalQuestion.get();
                    var findCorrectAlternative = question.getAlternatives().stream()
                            .filter(alternative -> alternative.isCorrect())
                            .findFirst();
                    
                    if (findCorrectAlternative.isPresent()) {
                        var correctAlternative = findCorrectAlternative.get();
                        if (correctAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                            questionAnswer.setCorrect(true);
                            correctAnswers.incrementAndGet() ;
                        } else {
                            questionAnswer.setCorrect(false);
                        }
                       var answersCertificationsEntity = AnswersCertificationsEntity.builder()
                        .answerID(questionAnswer.getAlternativeID())
                        .questionID(questionAnswer.getQuestionID())
                        .isCorrect(questionAnswer.isCorrect())
                        .build();
                        answersCertifications.add(answersCertificationsEntity);
                    } else {
                        
                    }
                } else {
                  
                }
            });
                // Verificar se existe student pelo email
                var student = studentRepository.findByEmail(dto.getEmail());
                UUID studentID;
                if (student.isEmpty()) {
                 var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
                 studentCreated = studentRepository.save(studentCreated);
                 studentID = studentCreated.getId();
                }
                else{
                    studentID = student.get().getId();
                }

 
            CertificationStudentEntity certificationStudentEntity = 
            CertificationStudentEntity.builder()
            .technology(dto.getTechnology())
            .studentID(studentID)
            .grade(correctAnswers.get())
            .build();

          
            var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);
        
            answersCertifications.stream().forEach(answersCertification -> { 
                answersCertification.setCertificationID(certificationStudentEntity.getId());
                answersCertification.setCertificationStudentEntity(certificationStudentEntity);
    });
            certificationStudentEntity.setAnswersCertificationsEntities(answersCertifications);
            certificationStudentRepository.save(certificationStudentEntity);
            return certificationStudentCreated;
        // Salvar as informações da certificação
    }
}


