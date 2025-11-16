package com.upc.ecovibeb.security.dtos;

import lombok.Data;
import jakarta.validation.constraints.Email;

@Data
public class PasswordForgotRequest {
    @Email
    private String email;
}