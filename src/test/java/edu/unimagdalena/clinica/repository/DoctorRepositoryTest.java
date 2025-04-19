package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Import(TestcontainersConfiguration.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DoctorRepositoryTest {

    @Autowired
    DoctorRepository doctorRepository;

    Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor(null, "Juan Avendaño", "juanavenda@gmail.com", "ginecologo", null, null);
        doctorRepository.save(doctor);
    }



    @Test
    void findById() {
    Optional<Doctor> doctorEncontrado = doctorRepository.findById(doctor.getId());

    assertTrue(doctorEncontrado.isPresent());
    assertEquals(doctor.getFullName(), doctorEncontrado.get().getFullName());
    assertEquals(doctor.getId(), doctorEncontrado.get().getId());
    }

    @Test
    void existsByEmail() {
        boolean exists = doctorRepository.existsByEmail("juanavenda@gmail.com");
        assertTrue(exists);
    }

    @Test
    void existsById() {
        assertTrue(doctorRepository.existsById(doctor.getId()));
    }
}