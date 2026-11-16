package com.mayara.cashflow.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("E-mail ou senha inválidos.");
    }
}