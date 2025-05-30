package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findById(Long id);
    boolean existsById(Long id);

}
