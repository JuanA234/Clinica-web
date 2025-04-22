package edu.unimagdalena.clinica.exception;

import edu.unimagdalena.clinica.entity.Doctor;

public class DoctorNotFoundException extends ResourceNotFoundException{
    public DoctorNotFoundException(String message) {
        super(message);
    }
}
