package edu.unimagdalena.clinica.exception.notFound;

public class DoctorNotFoundException extends ResourceNotFoundException {
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
