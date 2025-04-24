package edu.unimagdalena.clinica.service.interfaces;

import edu.unimagdalena.clinica.dto.Appointment.CreateAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.ResponseAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.UpdateAppointmentDTO;
import edu.unimagdalena.clinica.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    ResponseAppointmentDTO createAppointment(CreateAppointmentDTO request);

    List<ResponseAppointmentDTO> findAllAppointments();

    ResponseAppointmentDTO findAppointmentById(Long id);

    ResponseAppointmentDTO updateAppointment(Long id, UpdateAppointmentDTO request);

    void deleteAppointment(Long id);

    List<ResponseAppointmentDTO> findAppointmentByDoctorId(Long doctorId, LocalDate date);
}
