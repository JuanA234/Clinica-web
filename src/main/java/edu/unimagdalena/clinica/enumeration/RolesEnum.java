package edu.unimagdalena.clinica.enumeration;

public enum RolesEnum {
    DOCTOR("DOCTOR"),
    ADMIN("ADMIN");

    private final String role;

    private RolesEnum(String role) {
        this.role = role;
    }

}
