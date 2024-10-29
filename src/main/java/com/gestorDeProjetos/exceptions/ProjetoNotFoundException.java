package com.gestorDeProjetos.exceptions;

public class ProjetoNotFoundException extends RuntimeException {
    public ProjetoNotFoundException(String message) {
        super(message);
    }
}
