package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestcontainersConfiguration.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppointmentRepositoryTest {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ConsultRoomRepository consultRoomRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    Appointment appointment;
    Doctor doctor;

    @BeforeEach
    void setUp() {
        // Crear y guardar doctor
        doctor = Doctor.builder()
                .fullName("Doctor")
                .email("doctor@email.com")
                .specialty("Pediatría")
                .availableFrom(LocalTime.of(9, 0))
                .availableTo(LocalTime.of(17, 0))
                .build();
        doctorRepository.save(doctor);

        // Crear y guardar paciente
        Patient patient = Patient.builder()
                .fullName("Paciente Prueba")
                .email("paciente@correo.com")
                .phone("123456789")
                .build();

        patientRepository.save(patient);

        // Crear y guardar consultorio
        ConsultRoom room = ConsultRoom.builder()
                .name("Consultorio A")
                .build();
        consultRoomRepository.save(room);

        // Crear y guardar cita
        appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .consultRoom(room)
                .startTime(LocalDateTime.now().plusDays(1))
                .endTime(LocalDateTime.now().plusDays(1).plusHours(1))
                .status(Status.SCHEDULED)
                .build();
        appointmentRepository.save(appointment);


    }

    @Test
    void findById() {
        Optional<Appointment> encontrado = appointmentRepository.findById(appointment.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(appointment.getId(), encontrado.get().getId());
    }

    @Test
    void findByDoctor() {
        Optional<Appointment> encontrado = appointmentRepository.findByDoctor(doctor);

        assertTrue(encontrado.isPresent());
        assertEquals(doctor.getId(), encontrado.get().getDoctor().getId());
    }


    @Test
    void existsById() {
        assertTrue(appointmentRepository.existsById(appointment.getId()));
    }
}