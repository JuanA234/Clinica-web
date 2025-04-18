package edu.unimagdalena.clinica.service.interfaces;

import edu.unimagdalena.clinica.dto.ConsultRoom.RequestConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;

import java.util.List;

public interface ConsultRoomService {

    ResponseConsultRoomDTO createConsultRoom(RequestConsultRoomDTO request);

    List<ResponseConsultRoomDTO> findAllConsultRooms();

    ResponseConsultRoomDTO findConsultRoomById(Long id);

    ResponseConsultRoomDTO updateConsultRoomById(Long id, RequestConsultRoomDTO request);

    void deleteConsultRoomById(Long id);
}
