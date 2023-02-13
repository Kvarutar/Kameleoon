package com.voronchikhin.kameleoon.controllers;

import com.voronchikhin.kameleoon.auth.AuthenticationResponse;
import com.voronchikhin.kameleoon.dto.AuthenticationDTO;
import com.voronchikhin.kameleoon.dto.RegisterDTO;
import com.voronchikhin.kameleoon.services.AuthenticationService;
import com.voronchikhin.kameleoon.util.errors.*;
import com.voronchikhin.kameleoon.util.validators.RegistartionValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final RegistartionValidator registartionValidator;

    public AuthController(AuthenticationService authenticationService, RegistartionValidator registartionValidator) {
        this.authenticationService = authenticationService;
        this.registartionValidator = registartionValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterDTO registerDTO,
                                                           BindingResult bindingResult){


        registartionValidator.validate(registerDTO, bindingResult);
        if(bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new RegistrationException(errorBuilder.buildError());
        }

        return ResponseEntity.ok(authenticationService.register(registerDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationDTO authenticationDTO,
                                                               BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            ErrorBuilder errorBuilder = new ErrorBuilder(bindingResult);

            throw new AuthenticationException(errorBuilder.buildError());
        }

        return ResponseEntity.ok(authenticationService.authenticate(authenticationDTO));
    }

    @ExceptionHandler({RegistrationException.class, AuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exc){
        ErrorResponse response = new ErrorResponse(
                exc.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
