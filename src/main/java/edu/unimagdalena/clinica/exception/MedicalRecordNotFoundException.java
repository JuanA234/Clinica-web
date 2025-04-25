package edu.unimagdalena.clinica.exception;


public class MedicalRecordNotFoundException extends ResourceNotFoundException{
    public MedicalRecordNotFoundException(String message) {
        super(message);
    }
}
