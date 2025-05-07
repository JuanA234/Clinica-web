package edu.unimagdalena.clinica.security.services;


import edu.unimagdalena.clinica.dto.User.UserInfo;
import edu.unimagdalena.clinica.dto.User.UserLoginDTORequest;
import edu.unimagdalena.clinica.dto.User.UserLoginDTOResponse;
import edu.unimagdalena.clinica.entity.Role;
import edu.unimagdalena.clinica.entity.User;
import edu.unimagdalena.clinica.enumeration.RolesEnum;
import edu.unimagdalena.clinica.exception.notFound.RoleNotFoundException;
import edu.unimagdalena.clinica.repository.RoleRepository;
import edu.unimagdalena.clinica.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new UserInfoDetail(user);
    }

    public UserInfo addUser(UserInfo userInfo) {
        Role role = roleRepository.findByRole(RolesEnum.DOCTOR)
                .orElseThrow(()->new RoleNotFoundException("No existe el rol"));

        User user = new User(null, userInfo.user(), passwordEncoder.encode(userInfo.password()), userInfo.email(), Set.of(role));
        userRepository.save(user);
        return new UserInfo(user.getUsername(), user.getEmail(), userInfo.password(), role.getRole().name());
    }
}
