package edu.unimagdalena.clinica.mapper;

import edu.unimagdalena.clinica.dto.Appointment.CreateAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.ResponseAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.UpdateAppointmentDTO;
import edu.unimagdalena.clinica.entity.Appointment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "consultRoom.id", target = "consultRoomId")
    ResponseAppointmentDTO toDTO(Appointment appointment);


    @Mapping(target = "id", ignore = true)
    @Mapping(source = "patientId", target = "patient.id")
    @Mapping(source = "doctorId", target = "doctor.id")
    @Mapping(source = "consultRoomId", target = "consultRoom.id")
    Appointment toEntity(CreateAppointmentDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Appointment updateEntityFromDTO(UpdateAppointmentDTO dto, @MappingTarget Appointment appointment);
}
