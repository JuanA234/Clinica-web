package edu.unimagdalena.clinica.service;

import edu.unimagdalena.clinica.dto.Doctor.RequestDoctorDTO;
import edu.unimagdalena.clinica.dto.Patient.RequestPatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.entity.Patient;
import edu.unimagdalena.clinica.mapper.PatientMapper;
import edu.unimagdalena.clinica.repository.PatientRepository;
import edu.unimagdalena.clinica.service.impl.PatientServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    @Test
    void createPatient() {
        Patient patient = Patient.builder().id(1L).fullName("Juan Avendaño").email("jaavendano@gmail.com").build();
        ResponsePatientDTO responsePatient = new ResponsePatientDTO(1L, "Juan Avendaño", "jaavendano@gmail.com", null);
        RequestPatientDTO requestPatient = new RequestPatientDTO("Juan Avendaño", "jaavendano@gmail.com", null);

        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        when(patientMapper.toDTO(patient)).thenReturn(responsePatient);
        when(patientMapper.toEntity(requestPatient)).thenReturn(patient);

        ResponsePatientDTO createdPatient = patientService.createPatient(requestPatient);

        assertNotNull(createdPatient);
        assertEquals(responsePatient, createdPatient);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void findAllPatients() {
        Patient patient = new Patient();
        ResponsePatientDTO dto = new ResponsePatientDTO(1L, patient.getFullName(), "jaavendano@gmail.com", null);

        when(patientRepository.findAll()).thenReturn(List.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(dto);

        List<ResponsePatientDTO> result = patientService.findAllPatients();

        assertEquals(1, result.size());
        verify(patientRepository).findAll();
        verify(patientMapper).toDTO(patient);
    }

    @Test
    void findPatientById() {
        Long id = 1L;
        Patient patient = new Patient();
        ResponsePatientDTO dto = new ResponsePatientDTO(id, patient.getFullName(), "jaavendano@gmail.com", null);

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        when(patientMapper.toDTO(patient)).thenReturn(dto);

        ResponsePatientDTO result = patientService.findPatientById(id);

        assertNotNull(result);
        verify(patientRepository).findById(id);
        verify(patientMapper).toDTO(patient);
    }

    @Test
    void findPatientById_NotFound() {
        Long id = 2L;
        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> patientService.findPatientById(id));

        assertEquals("No se encontró el paciente", exception.getMessage());
    }

    @Test
    void updatePatientById() {
        Long id = 1L;
        Patient patient = new Patient();
        RequestPatientDTO dto = new RequestPatientDTO(patient.getFullName(), "jaavendano@gmail.com", null);
        ResponsePatientDTO responseDTO = new ResponsePatientDTO(id, patient.getFullName(), "jaavendano@gmail.com", null);

        when(patientRepository.findById(id)).thenReturn(Optional.of(patient));
        doAnswer(invocation -> {
            // simula que el mapper actualiza el objeto
            return null;
        }).when(patientMapper).updateEntityFromDTO(dto, patient);
        when(patientMapper.toDTO(patient)).thenReturn(responseDTO);

        ResponsePatientDTO result = patientService.updatePatientById(id, dto);

        assertNotNull(result);
        verify(patientRepository).findById(id);
        verify(patientMapper).updateEntityFromDTO(dto, patient);
        verify(patientMapper).toDTO(patient);
    }

    @Test
    void deletePatiendById() {
        Long id = 1L;

        when(patientRepository.existsById(id)).thenReturn(true);
        doNothing().when(patientRepository).deleteById(id);

        assertDoesNotThrow(() -> patientService.deletePatiendById(id));

        verify(patientRepository).existsById(id);
        verify(patientRepository).deleteById(id);
    }

    @Test
    void deletePatientById_WhenPatientDoesNotExist() {
        Long id = 2L;

        when(patientRepository.existsById(id)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> patientService.deletePatiendById(id));

        assertEquals("No se encontró el paciente", exception.getMessage());
        verify(patientRepository).existsById(id);
        verify(patientRepository, never()).deleteById(anyLong());
    }
}