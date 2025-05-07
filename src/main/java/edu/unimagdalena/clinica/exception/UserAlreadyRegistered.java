package edu.unimagdalena.clinica.exception;

public class UserAlreadyRegistered extends RuntimeException{
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}
