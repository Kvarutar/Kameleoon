package com.voronchikhin.kameleoon.util.validators;

import com.voronchikhin.kameleoon.dto.RegisterDTO;
import com.voronchikhin.kameleoon.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class RegistartionValidator implements Validator {

    private final PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterDTO registerDTO = (RegisterDTO) target;

        if (personService.findByEmail(registerDTO.getEmail()) != null){
            errors.rejectValue("email", "", "This email is invalid");
        }
    }
}
