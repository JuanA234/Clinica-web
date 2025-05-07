package edu.unimagdalena.clinica.controller;

import edu.unimagdalena.clinica.dto.User.UserInfo;
import edu.unimagdalena.clinica.dto.User.UserLoginDTORequest;
import edu.unimagdalena.clinica.security.jwt.JwtUtil;
import edu.unimagdalena.clinica.security.services.JpaUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final JpaUserDetailsService service;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<UserInfo> addNewUser(@RequestBody UserInfo userInfo) {
        UserInfo response = service.addUser(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody UserLoginDTORequest userLoginDTORequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTORequest.email(), userLoginDTORequest.password()));
        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(userLoginDTORequest.email());
            return ResponseEntity.ok(token);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
