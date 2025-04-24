package edu.unimagdalena.clinica.service.impl;

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
import edu.unimagdalena.clinica.service.interfaces.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ConsultRoomRepository consultRoomRepository;



    @Override
    public ResponseAppointmentDTO createAppointment(CreateAppointmentDTO request) {
        Doctor doctor = doctorRepository.findById(request.doctorId()).
                orElseThrow(()->new DoctorNotFoundException("Doctor con id: "+request.doctorId() + " no encontrado"));

        Patient patient = patientRepository.findById(request.patientId()).
                orElseThrow(()->new PatientNotFoundException("Paciente con id: "+request.patientId() + " no encontrado"));

        ConsultRoom consultRoom = consultRoomRepository.findById(request.consultRoomId()).
                orElseThrow(()-> new ConsultRoomNotFoundException("Consultorio con id: "+request.consultRoomId() + " no encontrado"));

        if (!appointmentRepository.findConflicts(doctor.getId(), consultRoom.getId(), request.startTime(),
                request.endTime()).isEmpty())
        {
            throw new AppointmentAlreadyExists("Una cita ya esta reservada para ese periodo de tiempo, revisa que la cita que " +
                    "estas creando no se solape con alguna existente");
        }

        LocalTime startTime = request.startTime().toLocalTime();
        LocalTime endTime = request.endTime().toLocalTime();

        if (startTime.isBefore(doctor.getAvailableFrom()) || endTime.isAfter(doctor.getAvailableTo())) {
            throw new AppointmentTimeNotAvailableException("La cita está fuera del horario disponible del doctor");
        }else if(request.startTime().isBefore(LocalDateTime.now()) || request.endTime().isBefore(request.startTime())
        ||startTime.isBefore(doctor.getAvailableFrom())){
            throw new AppointmentTimeNotAvailableException("Verificar que las fechas sean validas");
        }


        Appointment appointment = appointmentMapper.toEntity(request);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setConsultRoom(consultRoom);
        appointment.setStatus(Status.SCHEDULED);

        return appointmentMapper.toDTO(appointmentRepository.save(appointment));
    }

    @Override
    public List<ResponseAppointmentDTO> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDTO).toList();
    }

    @Override
    public ResponseAppointmentDTO findAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointmentMapper::toDTO)
                .orElseThrow(()-> new AppointmentNotFoundException("No se encontro la cita con el id: " + id));
    }

    @Override
    public ResponseAppointmentDTO updateAppointment(Long id, UpdateAppointmentDTO request) {
        Appointment foundAppointment = appointmentRepository.findById(id).orElseThrow(
                ()-> new AppointmentNotFoundException("No se encontro la cita con el id: " + id)
        );
        LocalTime startTime = request.startTime().toLocalTime();
        LocalTime endTime = request.endTime().toLocalTime();


        if(request.startTime().isBefore(LocalDateTime.now()) || request.endTime().isBefore(request.startTime())) {
            throw new AppointmentTimeNotAvailableException("El horario de tiempo incorrecto");
        }else if(startTime.isAfter(foundAppointment.getDoctor().getAvailableTo()) ||
        startTime.isBefore(foundAppointment.getDoctor().getAvailableFrom()) ||
                endTime.isAfter(foundAppointment.getDoctor().getAvailableTo())) {
            throw new AppointmentTimeNotAvailableException("La cita no puede ser fuera del horario de disponibilidad del doctor ");
        }else if(foundAppointment.getStatus()!= Status.SCHEDULED){
            throw new AppointmentTimeNotAvailableException("La cita ya fue completada o cancelada");
        }


        appointmentMapper.updateEntityFromDTO(request, foundAppointment);
        return appointmentMapper.toDTO(appointmentRepository.save(foundAppointment));
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)){
            throw new AppointmentNotFoundException("No se encontro la cita con el id: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<ResponseAppointmentDTO> findAppointmentByDoctorId(Long doctorId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return appointmentRepository.findByDoctorAndDate(doctorId, startOfDay, endOfDay)
                .stream().map(appointmentMapper::toDTO).toList();
    }


}
