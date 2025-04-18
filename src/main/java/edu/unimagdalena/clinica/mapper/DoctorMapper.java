package edu.unimagdalena.clinica.mapper;

import edu.unimagdalena.clinica.dto.Doctor.RequestDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.entity.Doctor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import javax.print.Doc;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    ResponseDoctorDTO toDTO(Doctor doctor);
    Doctor toEntity(RequestDoctorDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Doctor updateEntityFromDTO(RequestDoctorDTO dto, @MappingTarget Doctor doctor);
}
