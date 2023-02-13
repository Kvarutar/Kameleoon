package com.voronchikhin.kameleoon.services;

import com.voronchikhin.kameleoon.auth.AuthenticationResponse;
import com.voronchikhin.kameleoon.dto.AuthenticationDTO;
import com.voronchikhin.kameleoon.dto.RegisterDTO;
import com.voronchikhin.kameleoon.models.Person;
import com.voronchikhin.kameleoon.repositories.PersonRepository;
import com.voronchikhin.kameleoon.util.PersonDetails;
import com.voronchikhin.kameleoon.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterDTO registerDTO) throws UsernameNotFoundException{
        Optional<Person> checkPerson = personRepository.findByEmail(registerDTO.getEmail());

        if (checkPerson.isPresent()){
            throw new UsernameNotFoundException("User with this email already exist");
        }

        var user = Person.builder()
                .name(registerDTO.getName())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .role(Role.USER)
                .dateOfCreation(new Date())
                .build();

        personRepository.save(user);
        var jwtToken = jwtService.generateToken(new PersonDetails(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationDTO authenticationDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getEmail(),
                        authenticationDTO.getPassword()
                )
        );

        var user = personRepository.findByEmail(authenticationDTO.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(new PersonDetails(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
