package edu.unimagdalena.clinica.exception;

public class PatientNotFoundException extends ResourceNotFoundException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}
