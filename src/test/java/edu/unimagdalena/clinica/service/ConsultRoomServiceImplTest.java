package edu.unimagdalena.clinica.service;

import edu.unimagdalena.clinica.dto.ConsultRoom.CreateConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.UpdateConsultRoomDTO;
import edu.unimagdalena.clinica.entity.ConsultRoom;
import edu.unimagdalena.clinica.exception.ConsultRoomNotFoundException;
import edu.unimagdalena.clinica.mapper.ConsultRoomMapper;
import edu.unimagdalena.clinica.repository.ConsultRoomRepository;
import edu.unimagdalena.clinica.service.impl.ConsultRoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultRoomServiceImplTest {


    @Mock
    private ConsultRoomRepository consultRoomRepository;

    @Mock
    private ConsultRoomMapper consultRoomMapper;

    @InjectMocks
    private ConsultRoomServiceImpl consultRoomService;

    @Test
    void createConsultRoom() {
        CreateConsultRoomDTO request = new CreateConsultRoomDTO("Consultorio 1", "piso 1", "");
        ConsultRoom consultRoom = new ConsultRoom();
        ResponseConsultRoomDTO response = new ResponseConsultRoomDTO(1L, "consultorio", "piso 1", "");

        when(consultRoomMapper.toEntity(request)).thenReturn(consultRoom);
        when(consultRoomRepository.save(consultRoom)).thenReturn(consultRoom);
        when(consultRoomMapper.toDTO(consultRoom)).thenReturn(response);

        ResponseConsultRoomDTO result = consultRoomService.createConsultRoom(request);

        assertEquals(response, result);
    }

    @Test
    void findAllConsultRooms() {
        List<ConsultRoom> rooms = List.of(new ConsultRoom(), new ConsultRoom());

        ResponseConsultRoomDTO response1 = new ResponseConsultRoomDTO(1L, "consultorio 1", "piso 1", "");
        ResponseConsultRoomDTO response2 = new ResponseConsultRoomDTO(2L, "consultorio 2", "piso 2", "");

        when(consultRoomRepository.findAll()).thenReturn(rooms);
        when(consultRoomMapper.toDTO(any())).thenReturn(response1, response2);

        List<ResponseConsultRoomDTO> result = consultRoomService.findAllConsultRooms();

        assertEquals(2, result.size());
    }

    @Test
    void findConsultRoomById() {
        Long id = 1L;
        ConsultRoom entity = new ConsultRoom();
        ResponseConsultRoomDTO dto = new ResponseConsultRoomDTO(id, "consultorio", "piso 1", "");

        when(consultRoomRepository.findById(id)).thenReturn(Optional.of(entity));
        when(consultRoomMapper.toDTO(entity)).thenReturn(dto);

        ResponseConsultRoomDTO result = consultRoomService.findConsultRoomById(id);

        assertEquals(dto, result);

    }

    @Test
    void findConsultRoomById_whenNotFound() {
        Long id = 1L;
        when(consultRoomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ConsultRoomNotFoundException.class, () -> consultRoomService.findConsultRoomById(id));
    }

    @Test
    void updateConsultRoomById() {
        Long id = 1L;
        UpdateConsultRoomDTO request = new UpdateConsultRoomDTO("consultorio 1", "piso 1", "");
        ConsultRoom existing = new ConsultRoom();
        ConsultRoom updated = new ConsultRoom();
        ResponseConsultRoomDTO response = new ResponseConsultRoomDTO(id, "consultorio", "piso 1", "");

        when(consultRoomRepository.findById(id)).thenReturn(Optional.of(existing));
        doAnswer(invocation -> {
            request.getClass(); // simplemente para no dejarlo vacío
            return null;
        }).when(consultRoomMapper).updateEntityFromDTO(request, existing);
        when(consultRoomRepository.save(existing)).thenReturn(updated);
        when(consultRoomMapper.toDTO(updated)).thenReturn(response);

        ResponseConsultRoomDTO result = consultRoomService.updateConsultRoomById(id, request);

        assertEquals(response, result);
    }

    @Test
    void deleteConsultRoomById() {
        Long id = 1L;
        when(consultRoomRepository.existsById(id)).thenReturn(true);

        consultRoomService.deleteConsultRoomById(id);

        verify(consultRoomRepository).deleteById(id);
    }

    @Test
    void deleteConsultRoomById_whetherNotFound() {
        Long id = 1L;
        when(consultRoomRepository.existsById(id)).thenReturn(false);

        assertThrows(ConsultRoomNotFoundException.class, () -> consultRoomService.deleteConsultRoomById(id));
    }
}