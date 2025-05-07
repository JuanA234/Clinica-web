package edu.unimagdalena.clinica.enumeration;

public enum RolesEnum {
    DOCTOR("ROLE_DOCTOR"),
    ADMIN("ROLE_ADMIN");

    private final String role;

    private RolesEnum(String role) {
        this.role = role;
    }

}
