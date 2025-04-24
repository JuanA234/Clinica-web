package edu.unimagdalena.clinica.exception;

public class AppointmentAlreadyExists extends RuntimeException{
    public AppointmentAlreadyExists(String message) {
        super(message);
    }
}
