package edu.unimagdalena.clinica.controller;

import edu.unimagdalena.clinica.dto.User.UserInfo;
import edu.unimagdalena.clinica.dto.User.UserLoginDTORequest;
import edu.unimagdalena.clinica.dto.User.UserLoginDTOResponse;
import edu.unimagdalena.clinica.dto.User.UserRegisterDTORequest;
import edu.unimagdalena.clinica.security.jwt.JwtUtil;
import edu.unimagdalena.clinica.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<UserInfo> addNewUser(@Valid @RequestBody UserRegisterDTORequest request) {
        UserInfo response = authService.addUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginDTOResponse> authenticateAndGetToken(@RequestBody UserLoginDTORequest userLoginDTORequest) {
        return ResponseEntity.ok(authService.login(userLoginDTORequest));
    }
}
