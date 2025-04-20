package edu.unimagdalena.clinica.mapper;

import edu.unimagdalena.clinica.dto.Doctor.CreateDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.UpdateDoctorDTO;
import edu.unimagdalena.clinica.entity.Doctor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    ResponseDoctorDTO toDTO(Doctor doctor);
    Doctor toEntity(CreateDoctorDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Doctor updateEntityFromDTO(UpdateDoctorDTO dto, @MappingTarget Doctor doctor);
}
