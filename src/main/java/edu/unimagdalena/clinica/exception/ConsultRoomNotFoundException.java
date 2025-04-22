package edu.unimagdalena.clinica.exception;

public class ConsultRoomNotFoundException extends ResourceNotFoundException{
    public ConsultRoomNotFoundException(String message) {
        super(message);
    }
}
