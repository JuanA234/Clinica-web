package edu.unimagdalena.clinica.exception.notFound;

public class AppointmentNotFoundException extends ResourceNotFoundException {
    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
