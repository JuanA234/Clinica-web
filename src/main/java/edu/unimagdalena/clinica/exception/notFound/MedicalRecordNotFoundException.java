package edu.unimagdalena.clinica.exception.notFound;


public class MedicalRecordNotFoundException extends ResourceNotFoundException {
    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}
