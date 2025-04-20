package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestcontainersConfiguration.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    Patient patient;


    @BeforeEach
    void setUp() {
        patient = new Patient(null, "Juan Avendaño", "juanavenda@gmail.com", null, null, null, null);
        patientRepository.save(patient);
    }

    @Test
    void findById() {
        Optional<Patient> found = patientRepository.findById(patient.getId());

        assertTrue(found.isPresent());
        assertEquals(patient.getId(), found.get().getId());
        assertEquals(patient.getFullName(), found.get().getFullName());
    }

    @Test
    void existsById() {
        assertTrue(patientRepository.existsById(patient.getId()));
    }
}