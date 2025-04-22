package edu.unimagdalena.clinica.exception;

public class AppointmentNotFoundException extends ResourceNotFoundException{
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
