package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.Role;
import edu.unimagdalena.clinica.enumeration.RolesEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRole(RolesEnum role);
}
