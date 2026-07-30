package com.waongo.portfolio_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, message = "Le nom doit faire au moins 2 caractères")
    private String nom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le message est obligatoire")
    @Size(min = 10, message = "Le message doit faire au moins 10 caractères")
    private String message;
}