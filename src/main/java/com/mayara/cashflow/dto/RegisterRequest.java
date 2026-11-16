package com.mayara.cashflow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "O nome de usuário é obrigatório.")
        @Size(max = 100, message = "O nome de usuário deve conter no máximo 100 caracteres.")
        String name,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "E-mail inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres.")
        String password
) {
}
