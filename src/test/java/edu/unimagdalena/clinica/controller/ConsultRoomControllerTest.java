package edu.unimagdalena.clinica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.clinica.dto.ConsultRoom.CreateConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.UpdateConsultRoomDTO;
import edu.unimagdalena.clinica.service.interfaces.ConsultRoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultRoomController.class)
@Import(ConsultRoomControllerTest.MockConfig.class)
class ConsultRoomControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ConsultRoomService consultRoomService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ConsultRoomService consultRoomService() {
            return Mockito.mock(ConsultRoomService.class);
        }
    }

    @Test
    void getAllRooms() throws Exception {
        ResponseConsultRoomDTO response = new ResponseConsultRoomDTO(1L, "Room A", "1st Floor", "General consultation");
        when(consultRoomService.findAllConsultRooms()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Room A"));
    }

    @Test
    void getRoomById() throws Exception {
        ResponseConsultRoomDTO response = new ResponseConsultRoomDTO(1L, "Room B", "2nd Floor", "Specialist");
        when(consultRoomService.findConsultRoomById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Room B"));
    }

    @Test
    void createRoom() throws Exception {
        CreateConsultRoomDTO request = new CreateConsultRoomDTO("Room C", "3rd Floor", "Surgery");
        ResponseConsultRoomDTO response = new ResponseConsultRoomDTO(1L, "Room C", "3rd Floor", "Surgery");

        when(consultRoomService.createConsultRoom(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Room C"));
    }

    @Test
    void updateRoom() throws Exception {
        UpdateConsultRoomDTO request = new UpdateConsultRoomDTO("Room D", "4th Floor", "Cardiology");
        ResponseConsultRoomDTO response = new ResponseConsultRoomDTO(1L, "Room D", "4th Floor", "Cardiology");

        when(consultRoomService.updateConsultRoomById(1L, request)).thenReturn(response);

        mockMvc.perform(put("/api/v1/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.floor").value("4th Floor"));
    }

    @Test
    void deleteRoom() throws Exception {
        mockMvc.perform(delete("/api/v1/rooms/1"))
                .andExpect(status().isNoContent());

        verify(consultRoomService).deleteConsultRoomById(1L);
    }
}
