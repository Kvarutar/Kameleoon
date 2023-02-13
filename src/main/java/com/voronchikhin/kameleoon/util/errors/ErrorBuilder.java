package com.voronchikhin.kameleoon.util.errors;

import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ErrorBuilder {
    private BindingResult bindingResult;

    public String buildError(){
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errorList = bindingResult.getFieldErrors();

        for (FieldError error: errorList){
            errorMessage.append(error.getField())
                    .append(" - ")
                    .append(error.getDefaultMessage())
                    .append(";");
        }

        return errorMessage.toString();
    }
}
