package edu.unimagdalena.clinica.service;

import edu.unimagdalena.clinica.dto.MedicalRecord.CreateMedicalRecordDTO;
import edu.unimagdalena.clinica.dto.MedicalRecord.ResponseMedicalRecordDTO;
import edu.unimagdalena.clinica.entity.*;
import edu.unimagdalena.clinica.enumeration.Status;
import edu.unimagdalena.clinica.exception.*;
import edu.unimagdalena.clinica.exception.notFound.AppointmentNotFoundException;
import edu.unimagdalena.clinica.exception.notFound.MedicalRecordNotFoundException;
import edu.unimagdalena.clinica.exception.notFound.PatientNotFoundException;
import edu.unimagdalena.clinica.mapper.MedicalRecordMapper;
import edu.unimagdalena.clinica.repository.*;
import edu.unimagdalena.clinica.service.impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {

    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private MedicalRecordMapper medicalRecordMapper;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    private Appointment appointment;
    private Patient patient;
    private MedicalRecord medicalRecord;
    private CreateMedicalRecordDTO requestDTO;
    private ResponseMedicalRecordDTO responseDTO;
    private LocalDateTime createdAt;

    @BeforeEach
    void setUp() {

        createdAt = LocalDateTime.of(2025, 4, 24, 9, 30);

        appointment = new Appointment();
        appointment.setId(10L);
        appointment.setStatus(Status.COMPLETED);

        patient = new Patient();
        patient.setId(20L);

        medicalRecord = new MedicalRecord();
        medicalRecord.setId(100L);
        medicalRecord.setAppointment(appointment);
        medicalRecord.setPatient(patient);
        medicalRecord.setDiagnosis("Dx prueba");
        medicalRecord.setNotes("Notas prueba");
        medicalRecord.setCreatedAt(createdAt);

        requestDTO = new CreateMedicalRecordDTO(
                appointment.getId(),
                patient.getId(),
                "Dx prueba",
                "Notas prueba",
                createdAt
        );

        responseDTO = new ResponseMedicalRecordDTO(
                medicalRecord.getId(),
                appointment.getId(),
                patient.getId(),
                "Dx prueba",
                "Notas prueba",
                createdAt
        );
    }

    @Test
    void createRecord() {
        when(appointmentRepository.findById(10L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(20L)).thenReturn(Optional.of(patient));
        when(medicalRecordMapper.toEntity(requestDTO)).thenReturn(medicalRecord);
        when(medicalRecordRepository.save(medicalRecord)).thenReturn(medicalRecord);
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(responseDTO);

        ResponseMedicalRecordDTO result = medicalRecordService.createRecord(requestDTO);

        assertEquals(responseDTO, result);
        verify(medicalRecordRepository, times(1)).save(medicalRecord);
    }

    @Test
    void createRecordWhenAppointmentNotFound() {
        when(appointmentRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class,
                () -> medicalRecordService.createRecord(requestDTO));
    }

    @Test
    void createRecordWhenAppointmentNotCompleted() {
        appointment.setStatus(Status.SCHEDULED);
        when(appointmentRepository.findById(10L)).thenReturn(Optional.of(appointment));

        assertThrows(AppointmentTimeNotAvailableException.class,
                () -> medicalRecordService.createRecord(requestDTO));
    }

    @Test
    void createRecordWhenPatientNotFound() {
        when(appointmentRepository.findById(10L)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(20L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class,
                () -> medicalRecordService.createRecord(requestDTO));
    }

    @Test
    void findRecordById() {
        when(medicalRecordRepository.findById(100L)).thenReturn(Optional.of(medicalRecord));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(responseDTO);

        ResponseMedicalRecordDTO result = medicalRecordService.findRecordById(100L);

        assertEquals(responseDTO, result);
    }

    @Test
    void findRecordByIdWhenNotFound() {
        when(medicalRecordRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(MedicalRecordNotFoundException.class,
                () -> medicalRecordService.findRecordById(100L));
    }

    @Test
    void findRecordsByPatientId() {
        when(medicalRecordRepository.findByPatientId(20L)).thenReturn(List.of(medicalRecord));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(responseDTO);

        List<ResponseMedicalRecordDTO> result = medicalRecordService.findRecordsByPatientId(20L);

        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
    }

    @Test
    void findAllRecords() {
        when(medicalRecordRepository.findAll()).thenReturn(List.of(medicalRecord));
        when(medicalRecordMapper.toDTO(medicalRecord)).thenReturn(responseDTO);

        List<ResponseMedicalRecordDTO> result = medicalRecordService.findAllRecords();

        assertEquals(1, result.size());
        assertEquals(responseDTO, result.get(0));
    }

    @Test
    void deleteById() {
        when(medicalRecordRepository.findById(100L)).thenReturn(Optional.of(medicalRecord));

        medicalRecordService.deleteById(100L);

        verify(medicalRecordRepository).deleteById(100L);
    }

    @Test
    void deleteByIdWhenRecordNotFound() {
        when(medicalRecordRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(MedicalRecordNotFoundException.class,
                () -> medicalRecordService.deleteById(100L));
    }
}
