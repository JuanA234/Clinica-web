package edu.unimagdalena.clinica.exception.notFound;

public class RoleNotFoundException extends ResourceNotFoundException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
