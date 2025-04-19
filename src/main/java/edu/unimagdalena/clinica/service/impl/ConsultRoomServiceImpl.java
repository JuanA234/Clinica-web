package edu.unimagdalena.clinica.service.impl;

import edu.unimagdalena.clinica.dto.ConsultRoom.RequestConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;
import edu.unimagdalena.clinica.entity.ConsultRoom;
import edu.unimagdalena.clinica.mapper.ConsultRoomMapper;
import edu.unimagdalena.clinica.repository.ConsultRoomRepository;
import edu.unimagdalena.clinica.service.interfaces.ConsultRoomService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultRoomServiceImpl implements ConsultRoomService {

    private final ConsultRoomRepository consultRoomRepository;
    private final ConsultRoomMapper consultRoomMapper;

    public ConsultRoomServiceImpl(ConsultRoomRepository consultRoomRepository, ConsultRoomMapper consultRoomMapper) {
        this.consultRoomRepository = consultRoomRepository;
        this.consultRoomMapper = consultRoomMapper;
    }

    @Override
    public ResponseConsultRoomDTO createConsultRoom(RequestConsultRoomDTO request) {
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
                .orElseThrow(()-> new EntityNotFoundException("No se encontró el consultorio"));
        //TODO Cambiar a excepción personalizada
    }

    @Override
    public ResponseConsultRoomDTO updateConsultRoomById(Long id, RequestConsultRoomDTO request) {
        ConsultRoom foundConsultorio = consultRoomRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("No se encontró el consultorio"));
        consultRoomMapper.updateEntityFromDTO(request, foundConsultorio);
        return consultRoomMapper.toDTO(consultRoomRepository.save(foundConsultorio));
        //TODO Cambiar a excepción personalizada
    }

    @Override
    public void deleteConsultRoomById(Long id) {
        if (!consultRoomRepository.existsById(id)){
            throw new EntityNotFoundException("No se encontró el consultorio");
        }
        consultRoomRepository.deleteById(id);
        //TODO Cambiar a excepción personalizada
    }
}
