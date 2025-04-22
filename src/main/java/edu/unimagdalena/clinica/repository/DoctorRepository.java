package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.Doctor;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findById(Long id);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<Doctor> findBySpecialty(String specialty);



}
