package edu.unimagdalena.clinica.dto.User;

public record UserInfo(
        String user,
        String email,
        String password,
        String role
) {
}
