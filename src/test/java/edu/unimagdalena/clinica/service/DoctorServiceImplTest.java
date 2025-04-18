package edu.unimagdalena.clinica.service;

import edu.unimagdalena.clinica.dto.Doctor.RequestDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.entity.Doctor;
import edu.unimagdalena.clinica.mapper.DoctorMapper;
import edu.unimagdalena.clinica.repository.DoctorRepository;
import edu.unimagdalena.clinica.service.impl.DoctorServiceImpl;
import edu.unimagdalena.clinica.service.interfaces.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorMapper doctorMapper;

    @InjectMocks
    private DoctorServiceImpl doctorService;


    @Test
    void createDoctor() {
        // Arrange
        Doctor doctor = Doctor.builder().id(1L).fullName("Juan Avendaño").email("jaavendano@gmail.com").build();
        ResponseDoctorDTO responseDoctor = new ResponseDoctorDTO(1L, "Juan Avendaño",
                "jaavendano@gmail.com", null, null, null);
        RequestDoctorDTO requestDoctor = new RequestDoctorDTO("Juan Avendaño",
                "jaavendano@gmail.com", null, null, null);

        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);
        when(doctorMapper.toDTO(doctor)).thenReturn(responseDoctor);
        when(doctorMapper.toEntity(requestDoctor)).thenReturn(doctor);

        //Act
        ResponseDoctorDTO createdDoctor = doctorService.createDoctor(requestDoctor);

        //Assert
        assertNotNull(createdDoctor);
        assertEquals(responseDoctor, createdDoctor);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void findAllDoctors() {
        Doctor doctor1 = Doctor.builder().id(1L).fullName("Juan Avendaño").email("jaavendano@gmail.com").build();
        Doctor doctor2 = Doctor.builder().id(1L).fullName("Juan Sarmiento").email("jsarmiento@gmail.com").build();
        List<Doctor> doctors = List.of(doctor1, doctor2);


        ResponseDoctorDTO responseDoctor1 = new ResponseDoctorDTO(1L, "Juan Avendaño", "jaavendano@gmail.com", null, null, null);
        ResponseDoctorDTO responseDoctor2 = new ResponseDoctorDTO(1L, "Juan Sarmiento", "jsarmiento@gmail.com", null, null, null);

        when(doctorRepository.findAll()).thenReturn(List.of(doctor1, doctor2));
        when(doctorMapper.toDTO(any(Doctor.class))).thenReturn(responseDoctor1, responseDoctor2);

        List<ResponseDoctorDTO> result = doctorService.findAllDoctors();
        assertEquals(2, result.size());


    }

    @Test
    void findDoctorById() {
        Long id = 1L;
        Doctor doctor = new Doctor();
        ResponseDoctorDTO response = new ResponseDoctorDTO(id, "Laura Torres", "laura@gmail.com", "Ginecóloga", null, null);

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDTO(doctor)).thenReturn(response);

        ResponseDoctorDTO result = doctorService.findDoctorById(id);

        assertEquals(response, result);
    }

    @Test
    void findDoctorById_whenNotFound() {
        Long id = 99L;
        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> doctorService.findDoctorById(id));
    }

    @Test
    void updateDoctorById() {
        Long id = 1L;
        Doctor doctor = new Doctor();
        RequestDoctorDTO request = new RequestDoctorDTO("Nuevo Nombre", "nuevo@mail.com", "Cardiólogo", null, null  );
        ResponseDoctorDTO response = new ResponseDoctorDTO(id, "Nuevo Nombre", "nuevo@mail.com", "Cardiólogo", null, null);

        when(doctorRepository.findById(id)).thenReturn(Optional.of(doctor));
        when(doctorMapper.updateEntityFromDTO(request, doctor)).thenReturn(doctor);
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        when(doctorMapper.toDTO(doctor)).thenReturn(response);

        ResponseDoctorDTO result = doctorService.updateDoctorById(id, request);

        assertEquals(response, result);
    }

    @Test
    void testUpdateDoctorById_whenNotFound() {
        Long id = 999L;
        RequestDoctorDTO requestDoctor = new RequestDoctorDTO("Juan Avendaño",
                "jaavendano@gmail.com", null, null, null);


        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> doctorService.updateDoctorById(id, requestDoctor));
    }

    @Test
    void deleteDoctorById() {
        Long id = 5L;
        when(doctorRepository.existsById(id)).thenReturn(true);

        doctorService.deleteDoctorById(id);

        verify(doctorRepository).deleteById(id);
    }

    @Test
    void deleteDoctorById_whenNotFound() {
        Long id = 404L;
        when(doctorRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> doctorService.deleteDoctorById(id));
    }
}