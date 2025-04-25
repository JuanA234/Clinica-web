package edu.unimagdalena.clinica.mapper;

import edu.unimagdalena.clinica.dto.MedicalRecord.CreateMedicalRecordDTO;
import edu.unimagdalena.clinica.dto.MedicalRecord.ResponseMedicalRecordDTO;
import edu.unimagdalena.clinica.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "patient.id", target = "patientId")
    ResponseMedicalRecordDTO toDTO(MedicalRecord entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "appointmentId", target = "appointment.id")
    @Mapping(source = "patientId", target = "patient.id")
    MedicalRecord toEntity(CreateMedicalRecordDTO dto);

}
