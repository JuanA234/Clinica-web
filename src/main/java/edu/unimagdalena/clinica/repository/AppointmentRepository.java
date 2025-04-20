package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.Appointment;
import edu.unimagdalena.clinica.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long id);
    Optional<Appointment> findByDoctor(Doctor doctor);
    boolean existsById(Long id);


}
