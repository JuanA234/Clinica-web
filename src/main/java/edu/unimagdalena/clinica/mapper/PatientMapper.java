package edu.unimagdalena.clinica.mapper;

import edu.unimagdalena.clinica.dto.Patient.RequestPatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.entity.Patient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    ResponsePatientDTO toDTO(Patient patient);
    Patient toEntity(RequestPatientDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Patient updateEntityFromDTO(RequestPatientDTO dto, @MappingTarget Patient patient);
}
