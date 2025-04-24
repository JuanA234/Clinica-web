package edu.unimagdalena.clinica.exception;

public class AppointmentTimeNotAvailableException extends RuntimeException{
    public AppointmentTimeNotAvailableException(String message) {
        super(message);
    }
}
