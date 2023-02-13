package com.voronchikhin.kameleoon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDTO {
    @NotBlank
    private String email;

    @NotBlank
    String password;
}
