package edu.unimagdalena.clinica.service;

import edu.unimagdalena.clinica.dto.User.UserInfo;
import edu.unimagdalena.clinica.dto.User.UserLoginDTORequest;
import edu.unimagdalena.clinica.dto.User.UserLoginDTOResponse;
import edu.unimagdalena.clinica.dto.User.UserRegisterDTORequest;
import edu.unimagdalena.clinica.entity.Role;
import edu.unimagdalena.clinica.entity.User;
import edu.unimagdalena.clinica.exception.UserAlreadyRegistered;
import edu.unimagdalena.clinica.exception.UserLoginException;
import edu.unimagdalena.clinica.exception.notFound.RoleNotFoundException;
import edu.unimagdalena.clinica.repository.RoleRepository;
import edu.unimagdalena.clinica.repository.UserRepository;
import edu.unimagdalena.clinica.security.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserInfo addUser(UserRegisterDTORequest request) {

        if(userRepository.findByUsername(request.user()).isPresent()) {
          throw new UserAlreadyRegistered("El usuario ya existe");
        }

       Role role = roleRepository.findByRole(request.role())
               .orElseThrow(() -> new RoleNotFoundException("Role no encontrado"));

        User newUser = User.builder()
                .username(request.user())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(role))
                .build();

        userRepository.save(newUser);
      return new UserInfo(
              newUser.getUsername(),
              newUser.getEmail(),
              "******",
              role.getRole().name()
      );
    }

    public UserLoginDTOResponse login(UserLoginDTORequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));


        if(!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new UserLoginException("Username or password incorrect");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        return new UserLoginDTOResponse(user.getRoles().stream()
                .findFirst()
                .map(r->r.getRole().name())
                .orElse("UNKNOWN"), jwtUtil.generateToken(user.getUsername()));
    }
}
