package edu.unimagdalena.clinica.service.interfaces;

import edu.unimagdalena.clinica.dto.ConsultRoom.CreateConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.UpdateConsultRoomDTO;

import java.util.List;

public interface ConsultRoomService {

    ResponseConsultRoomDTO createConsultRoom(CreateConsultRoomDTO request);

    List<ResponseConsultRoomDTO> findAllConsultRooms();

    ResponseConsultRoomDTO findConsultRoomById(Long id);

    ResponseConsultRoomDTO updateConsultRoomById(Long id, UpdateConsultRoomDTO request);

    void deleteConsultRoomById(Long id);
}
