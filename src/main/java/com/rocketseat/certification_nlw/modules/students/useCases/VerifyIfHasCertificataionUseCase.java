package com.rocketseat.certification_nlw.modules.students.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCeriticationDTO;
import com.rocketseat.certification_nlw.modules.students.repositories.CertificationStudentRepository;

@Service
public class VerifyIfHasCertificataionUseCase {
    
    @Autowired 
    private CertificationStudentRepository repository;

    public boolean execute(VerifyHasCeriticationDTO dto){
      var result = this.repository.findByStudentEmailAndTechNology(dto.getEmail(),dto.getTechnology());
        if (!result.isEmpty()) {
            return true;
        }
        return false;
    }
}
