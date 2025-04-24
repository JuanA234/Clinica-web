package edu.unimagdalena.clinica.service;

import edu.unimagdalena.clinica.dto.Appointment.CreateAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.ResponseAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.UpdateAppointmentDTO;
import edu.unimagdalena.clinica.entity.*;
import edu.unimagdalena.clinica.exception.*;
import edu.unimagdalena.clinica.mapper.AppointmentMapper;
import edu.unimagdalena.clinica.repository.AppointmentRepository;
import edu.unimagdalena.clinica.repository.ConsultRoomRepository;
import edu.unimagdalena.clinica.repository.DoctorRepository;
import edu.unimagdalena.clinica.repository.PatientRepository;
import edu.unimagdalena.clinica.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ConsultRoomRepository consultRoomRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    Doctor doctor = Doctor.builder().id(1L).availableFrom(LocalTime.of(0,0))
            .availableTo(LocalTime.of(23, 59)).build();
    ConsultRoom consultRoom = ConsultRoom.builder().id(1L).build();
    Patient patient = Patient.builder().id(1L).build();

    LocalDateTime start = LocalDateTime.now().plusDays(1).withNano(0);
    LocalDateTime end = start.plusHours(1);


    Appointment appointment = Appointment.builder().id(1L).doctor(doctor).consultRoom(consultRoom).patient(patient).startTime(start).endTime(end)
            .build();
    ResponseAppointmentDTO response = new ResponseAppointmentDTO(1L, patient.getId(), doctor.getId(),
            consultRoom.getId(),start,end, Status.SCHEDULED);


    @Test
    void createAppointment() {

        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(patient.getId(), doctor.getId(), consultRoom.getId(), start, end);

        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(consultRoomRepository.findById(consultRoom.getId())).thenReturn(Optional.of(consultRoom));

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);
        when(appointmentMapper.toDTO(appointment)).thenReturn(response);
        when(appointmentMapper.toEntity(createAppointmentDTO)).thenReturn(appointment);

        ResponseAppointmentDTO appointmentCreated = appointmentService.createAppointment(createAppointmentDTO);

        assertNotNull(appointmentCreated);
        assertEquals(response, appointmentCreated);
        verify(appointmentRepository, times(1)).save(appointment);

    }

    @Test
    void createAppointmentWhenDoctorNotFound() {
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(patient.getId(), 2L, consultRoom.getId(), start, end);
        when(doctorRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> appointmentService.createAppointment(createAppointmentDTO));
    }

    @Test
    void createAppointmentWhenPatientNotFound(){
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(2L, doctor.getId(), consultRoom.getId(), start, end);

        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class, () -> appointmentService.createAppointment(createAppointmentDTO));
    }

    @Test
    void createAppointmentWhenRoomNotFound(){
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(patient.getId(), doctor.getId(), 2L, start, end);

        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(consultRoomRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ConsultRoomNotFoundException.class, () -> appointmentService.createAppointment(createAppointmentDTO));
    }

    @Test
    void createAppointmentWhenfindConflicts(){
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(patient.getId(), doctor.getId(), consultRoom.getId(), start, end);


        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(consultRoomRepository.findById(consultRoom.getId())).thenReturn(Optional.of(consultRoom));

        when(appointmentRepository.findConflicts(doctor.getId(), consultRoom.getId(), start, end)).thenReturn(List.of(appointment));

        assertThrows(AppointmentAlreadyExists.class, () -> appointmentService.createAppointment(createAppointmentDTO));
    }

    @Test
    void createAppointmentWhenDoctorNotAvaliable(){
        Doctor doctor = Doctor.builder().id(1L).availableFrom(LocalTime.of(8,0))
                .availableTo(LocalTime.of(18, 0)).build();
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(patient.getId(), doctor.getId(), consultRoom.getId(), LocalDateTime.of(2025, 5, 20, 6, 15), end);

        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(consultRoomRepository.findById(consultRoom.getId())).thenReturn(Optional.of(consultRoom));

        AppointmentTimeNotAvailableException exception = assertThrows(AppointmentTimeNotAvailableException.class, () -> appointmentService.createAppointment(createAppointmentDTO));

        assertEquals("La cita está fuera del horario disponible del doctor", exception.getMessage());
    }


    @Test
    void createAppointmentWhenTimeNotAvailable(){
        CreateAppointmentDTO createAppointmentDTO = new CreateAppointmentDTO(patient.getId(), doctor.getId(), consultRoom.getId(), LocalDateTime.of(2024, 5, 20, 8, 15), end);

        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patient.getId())).thenReturn(Optional.of(patient));
        when(consultRoomRepository.findById(consultRoom.getId())).thenReturn(Optional.of(consultRoom));

        AppointmentTimeNotAvailableException exception = assertThrows(AppointmentTimeNotAvailableException.class, () -> appointmentService.createAppointment(createAppointmentDTO));

        assertEquals("Verificar que las fechas sean validas", exception.getMessage());
    }



    @Test
    void findAllAppointments() {
        Appointment appointment1 = new Appointment(2L,patient, doctor, consultRoom, LocalDateTime.of(2025, 5, 20, 8, 15), end, Status.SCHEDULED, null);

        ResponseAppointmentDTO response2= new ResponseAppointmentDTO(2L, patient.getId(), doctor.getId(),
                consultRoom.getId(),LocalDateTime.of(2025, 5, 20, 8, 15),end, Status.SCHEDULED);

        when(appointmentRepository.findAll()).thenReturn(List.of(appointment,appointment1));
        when(appointmentMapper.toDTO(any())).thenReturn(response, response2);

        List<ResponseAppointmentDTO> appointments = appointmentService.findAllAppointments();

        assertEquals(2, appointments.size());
    }

    @Test
    void findAppointmentById() {

        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(appointmentMapper.toDTO(appointment)).thenReturn(response);

        ResponseAppointmentDTO appointmentFound = appointmentService.findAppointmentById(appointment.getId());

        assertNotNull(appointmentFound);
        assertEquals(response,appointmentFound);

    }

    @Test
    void findAppointmentByIdWhenNotFound(){
        when(appointmentRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(AppointmentNotFoundException.class, () -> appointmentService.findAppointmentById(2L));
    }

    @Test
    void updateAppointment() {
        appointment.setStatus(Status.SCHEDULED);

        UpdateAppointmentDTO update = new UpdateAppointmentDTO(start, end, Status.CANCELED);
        ResponseAppointmentDTO response = new ResponseAppointmentDTO(appointment.getId(), patient.getId(),doctor.getId(),
                consultRoom.getId(), appointment.getStartTime(), appointment.getEndTime(), Status.CANCELED);

        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));
        when(appointmentMapper.updateEntityFromDTO(update,appointment)).thenReturn(appointment);
        when(appointmentRepository.save(appointment)).thenReturn(appointment);
        when(appointmentMapper.toDTO(appointment)).thenReturn(response);

        ResponseAppointmentDTO result = appointmentService.updateAppointment(appointment.getId(), update);

        assertEquals(response,result);

    }

    @Test
    void updateAppointmentWhenNotFound() {

        UpdateAppointmentDTO update = new UpdateAppointmentDTO(start, end, Status.CANCELED);
        when(appointmentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class,()-> appointmentService.updateAppointment(2L, update));

    }

    @Test
    void updateAppointmentWhenDoctorNotAvaliable() {
        Doctor doctor = Doctor.builder().id(1L).availableFrom(LocalTime.of(8,0))
                .availableTo(LocalTime.of(18, 0)).build();

        Appointment appointment1 = Appointment.builder().id(1L).doctor(doctor).consultRoom(consultRoom).patient(patient).startTime(start).endTime(end)
                .build();
        UpdateAppointmentDTO update = new UpdateAppointmentDTO(LocalDateTime.of(2025, 5, 20, 6, 15), LocalDateTime.of(2025, 5, 20, 8, 15), Status.SCHEDULED);
        when(appointmentRepository.findById(appointment1.getId())).thenReturn(Optional.of(appointment1));

        AppointmentTimeNotAvailableException exception = assertThrows(AppointmentTimeNotAvailableException.class, () -> appointmentService.updateAppointment(appointment1.getId(),update));

        assertEquals("La cita no puede ser fuera del horario de disponibilidad del doctor ", exception.getMessage());

    }

    @Test
    void updateAppointmentWhenTimeNotAvailable() {
        Doctor doctor = Doctor.builder().id(1L).availableFrom(LocalTime.of(8,0))
                .availableTo(LocalTime.of(18, 0)).build();

        Appointment appointment1 = Appointment.builder().id(1L).doctor(doctor).consultRoom(consultRoom).patient(patient).startTime(start).endTime(end)
                .build();
        UpdateAppointmentDTO update = new UpdateAppointmentDTO(LocalDateTime.of(2025, 5, 20, 6, 15), end, Status.SCHEDULED);
        when(appointmentRepository.findById(appointment1.getId())).thenReturn(Optional.of(appointment1));

        AppointmentTimeNotAvailableException exception = assertThrows(AppointmentTimeNotAvailableException.class, () -> appointmentService.updateAppointment(appointment1.getId(),update));

        assertEquals("El horario de tiempo incorrecto", exception.getMessage());

    }

    @Test
    void updateAppointmentWhenAppointmentNotAvailable() {
        appointment.setStatus(Status.CANCELED);

        UpdateAppointmentDTO update = new UpdateAppointmentDTO(LocalDateTime.of(2025, 5, 20, 6, 15), end, Status.SCHEDULED);
        when(appointmentRepository.findById(appointment.getId())).thenReturn(Optional.of(appointment));

        AppointmentTimeNotAvailableException exception = assertThrows(AppointmentTimeNotAvailableException.class, () -> appointmentService.updateAppointment(appointment.getId(),update));

        assertEquals("El horario de tiempo incorrecto", exception.getMessage());

    }


    @Test
    void deleteAppointment() {

        when(appointmentRepository.existsById(appointment.getId())).thenReturn(true);

        appointmentService.deleteAppointment(appointment.getId());

        verify(appointmentRepository).deleteById(appointment.getId());
    }


    @Test
    void deleteAppointmentWhenNotFound() {

        when(appointmentRepository.existsById(2L)).thenReturn(false);

        assertThrows(AppointmentNotFoundException.class,()-> appointmentService.deleteAppointment(2L));
    }

    @Test
    void findAppointmentByDoctorId() {
        LocalDate date = start.toLocalDate();

        when(appointmentRepository.findByDoctorAndDate(appointment.getDoctor().getId(), date.atStartOfDay(), date.plusDays(1).atStartOfDay())).thenReturn(
                List.of(appointment)
        );
        when(appointmentMapper.toDTO(appointment)).thenReturn(response);
        List<ResponseAppointmentDTO> result = appointmentService.findAppointmentByDoctorId(appointment.getDoctor().getId(), date);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
    }
}