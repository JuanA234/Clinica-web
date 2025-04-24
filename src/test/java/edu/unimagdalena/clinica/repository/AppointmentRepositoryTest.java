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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
    LocalDateTime start = LocalDateTime.now().plusDays(1).withNano(0);
    LocalDateTime end = start.plusHours(1);

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
                .startTime(start)
                .endTime(end)
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
    void findByDoctorAndDate() {
        // Día de la cita
        LocalDate targetDate = LocalDate.now().plusDays(1);
        LocalDateTime startOfDay = targetDate.atStartOfDay();
        LocalDateTime endOfDay = targetDate.plusDays(1).atStartOfDay();

        // Guardamos otra cita en ese mismo día (misma doctora)
        Appointment otraCita = Appointment.builder()
                .doctor(doctor)
                .patient(patientRepository.findAll().get(0))
                .consultRoom(consultRoomRepository.findAll().get(0))
                .startTime(startOfDay.plusHours(2))
                .endTime(startOfDay.plusHours(3))
                .status(Status.SCHEDULED)
                .build();
        appointmentRepository.save(otraCita);

        // Ejecutamos la query
        List<Appointment> citas = appointmentRepository.findByDoctorAndDate(doctor.getId(), startOfDay, endOfDay);

        // Verificamos que retorne 2 citas
        assertEquals(2, citas.size());
        assertTrue(citas.stream().allMatch(c -> c.getDoctor().getId().equals(doctor.getId())));
        assertTrue(citas.stream().allMatch(c -> !c.getStartTime().isBefore(startOfDay) && c.getStartTime().isBefore(endOfDay)));
    }


    @Test
    void existsById() {
        assertTrue(appointmentRepository.existsById(appointment.getId()));
    }

    @Test
    void findConflicts() {
        List<Appointment> appointments = appointmentRepository.findConflicts(appointment.getDoctor().getId()
        , appointment.getConsultRoom().getId(), start, end);
        assertEquals(appointment.getId(), appointments.get(0).getId());
    }
}