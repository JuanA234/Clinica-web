package edu.unimagdalena.clinica.service.impl;

import edu.unimagdalena.clinica.dto.ConsultRoom.CreateConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.UpdateConsultRoomDTO;
import edu.unimagdalena.clinica.entity.ConsultRoom;
import edu.unimagdalena.clinica.exception.ConsultRoomNotFoundException;
import edu.unimagdalena.clinica.mapper.ConsultRoomMapper;
import edu.unimagdalena.clinica.repository.ConsultRoomRepository;
import edu.unimagdalena.clinica.service.interfaces.ConsultRoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultRoomServiceImpl implements ConsultRoomService{

    private final ConsultRoomRepository consultRoomRepository;
    private final ConsultRoomMapper consultRoomMapper;

    public ConsultRoomServiceImpl(ConsultRoomRepository consultRoomRepository, ConsultRoomMapper consultRoomMapper) {
        this.consultRoomRepository = consultRoomRepository;
        this.consultRoomMapper = consultRoomMapper;
    }

    @Override
    public ResponseConsultRoomDTO createConsultRoom(CreateConsultRoomDTO request) {
        return consultRoomMapper.toDTO(consultRoomRepository.save(consultRoomMapper.toEntity(request)));
    }

    @Override
    public List<ResponseConsultRoomDTO> findAllConsultRooms() {
        return consultRoomRepository.findAll().stream()
                .map(consultRoomMapper::toDTO).toList();
    }

    @Override
    public ResponseConsultRoomDTO findConsultRoomById(Long id) {
        return  consultRoomRepository.findById(id)
                .map(consultRoomMapper::toDTO)
                .orElseThrow(()-> new ConsultRoomNotFoundException("No se encontro el consultorio con el id: " + id));
    }

    @Override
    public ResponseConsultRoomDTO updateConsultRoomById(Long id, UpdateConsultRoomDTO request) {
        ConsultRoom foundConsultorio = consultRoomRepository.findById(id).
                orElseThrow(()-> new ConsultRoomNotFoundException("No se encontro el consultorio con el id: " + id));
        consultRoomMapper.updateEntityFromDTO(request, foundConsultorio);
        return consultRoomMapper.toDTO(consultRoomRepository.save(foundConsultorio));
    }

    @Override
    public void deleteConsultRoomById(Long id) {
        if (!consultRoomRepository.existsById(id)){
            throw new ConsultRoomNotFoundException("No se encontro el consultorio con el id: " + id);
        }
        consultRoomRepository.deleteById(id);
    }
}
