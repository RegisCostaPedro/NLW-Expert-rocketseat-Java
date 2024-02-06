package com.rocketseat.certification_nlw.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rocketseat.certification_nlw.modules.students.dto.VerifyHasCeriticationDTO;
import com.rocketseat.certification_nlw.modules.students.useCases.VerifyIfHasCertificataionUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    //Precisa usar o Use Case/Services
    @Autowired
    private VerifyIfHasCertificataionUseCase verifyIfHasCertificataionUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCeriticationDTO verifyHasCeriticationDTO){
        //Email
        //Technology
        var result = this.verifyIfHasCertificataionUseCase.execute(verifyHasCeriticationDTO);
        if (result) {
            return  "User alredy do the exam";
        }
        System.out.println(verifyHasCeriticationDTO);
        return "User can do the exam";
    }
}   
