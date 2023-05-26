package com.gamereview.api.exceptions;

public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException() {
        super("Senha inválida");
    }
}
