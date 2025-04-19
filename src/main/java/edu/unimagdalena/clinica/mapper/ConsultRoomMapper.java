package edu.unimagdalena.clinica.mapper;


import edu.unimagdalena.clinica.dto.ConsultRoom.RequestConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;
import edu.unimagdalena.clinica.entity.ConsultRoom;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ConsultRoomMapper {
    ResponseConsultRoomDTO toDTO(ConsultRoom consultRoom);
    ConsultRoom toEntity(RequestConsultRoomDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ConsultRoom updateEntityFromDTO(RequestConsultRoomDTO dto, @MappingTarget ConsultRoom consultRoom);
}
