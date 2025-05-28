package edu.unimagdalena.clinica.config;

import edu.unimagdalena.clinica.entity.Role;
import edu.unimagdalena.clinica.enumeration.RolesEnum;
import edu.unimagdalena.clinica.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (RolesEnum roleEnum : RolesEnum.values()) {
            roleRepository.findByRole(roleEnum)
                    .orElseGet(() -> roleRepository.save(new Role(null, roleEnum, Set.of())));
        }
    }
}
