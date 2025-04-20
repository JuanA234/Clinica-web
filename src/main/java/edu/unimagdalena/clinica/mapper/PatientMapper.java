package edu.unimagdalena.clinica.mapper;

import edu.unimagdalena.clinica.dto.Patient.CreatePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.UpdatePatientDTO;
import edu.unimagdalena.clinica.entity.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    ResponsePatientDTO toDTO(Patient patient);
    Patient toEntity(CreatePatientDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Patient updateEntityFromDTO(UpdatePatientDTO dto, @MappingTarget Patient patient);
}
