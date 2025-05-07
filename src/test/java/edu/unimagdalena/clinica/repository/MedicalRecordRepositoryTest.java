package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.*;
import edu.unimagdalena.clinica.enumeration.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(TestcontainersConfiguration.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicalRecordRepositoryTest {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultRoomRepository consultRoomRepository;

    private Patient patient;
    private Appointment appointment;
    private MedicalRecord medicalRecord;

    @BeforeEach
    void setUp() {
        // Crear doctor
        Doctor doctor = Doctor.builder()
                .fullName("Dra. Elena Ruiz")
                .email("elena.ruiz@example.com")
                .specialty("Cardiología")
                .availableFrom(java.time.LocalTime.of(8, 0))
                .availableTo(java.time.LocalTime.of(16, 0))
                .build();
        doctorRepository.save(doctor);

        // Crear paciente
        patient = Patient.builder()
                .fullName("Carlos Ramírez")
                .email("carlos@example.com")
                .phone("3109876543")
                .build();
        patientRepository.save(patient);

        // Crear consultorio
        ConsultRoom room = ConsultRoom.builder()
                .name("Consultorio B")
                .build();
        consultRoomRepository.save(room);

        // Crear cita
        LocalDateTime start = LocalDateTime.now().plusDays(1).withNano(0);
        LocalDateTime end = start.plusHours(1);
        appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .consultRoom(room)
                .startTime(start)
                .endTime(end)
                .status(Status.SCHEDULED)
                .build();
        appointmentRepository.save(appointment);

        // Crear historial médico
        medicalRecord = MedicalRecord.builder()
                .appointment(appointment)
                .patient(patient)
                .diagnosis("Hipertensión leve")
                .notes("Recomendación de dieta y ejercicio")
                .createdAt(LocalDateTime.now())
                .build();
        medicalRecordRepository.save(medicalRecord);
    }

    @Test
    void findById() {
        Optional<MedicalRecord> found = medicalRecordRepository.findById(medicalRecord.getId());

        assertTrue(found.isPresent());
        assertEquals(medicalRecord.getDiagnosis(), found.get().getDiagnosis());
        assertEquals(medicalRecord.getAppointment().getId(), found.get().getAppointment().getId());
    }

    @Test
    void findByPatientId() {
        List<MedicalRecord> records = medicalRecordRepository.findByPatientId(patient.getId());

        assertEquals(1, records.size());
        assertEquals(patient.getId(), records.get(0).getPatient().getId());
    }
}
