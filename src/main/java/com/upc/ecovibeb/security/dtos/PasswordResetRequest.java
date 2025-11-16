package com.upc.ecovibeb.security.dtos;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String newPassword;
}