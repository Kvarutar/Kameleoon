package com.voronchikhin.kameleoon.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
