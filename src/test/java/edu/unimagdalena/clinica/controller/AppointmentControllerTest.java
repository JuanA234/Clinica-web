package edu.unimagdalena.clinica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.clinica.dto.Appointment.CreateAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.ResponseAppointmentDTO;
import edu.unimagdalena.clinica.dto.Appointment.UpdateAppointmentDTO;
import edu.unimagdalena.clinica.entity.Status;
import edu.unimagdalena.clinica.service.interfaces.AppointmentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
@Import(AppointmentControllerTest.MockConfig.class)
class AppointmentControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private AppointmentService appointmentService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public AppointmentService appointmentService() {
            return Mockito.mock(AppointmentService.class);
        }
    }

    @Test
    void getAllAppointments() throws Exception {
        ResponseAppointmentDTO response = new ResponseAppointmentDTO(
                1L, 1L, 2L, 3L,
                LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                Status.SCHEDULED
        );
        when(appointmentService.findAllAppointments()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getAppointmentById() throws Exception {
        ResponseAppointmentDTO response = new ResponseAppointmentDTO(
                1L, 1L, 2L, 3L,
                LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                Status.SCHEDULED
        );
        when(appointmentService.findAppointmentById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/appointments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createAppointment() throws Exception {
        CreateAppointmentDTO request = new CreateAppointmentDTO(
                1L, 2L, 3L, LocalDateTime.now(), LocalDateTime.now().plusHours(1)
        );
        ResponseAppointmentDTO response = new ResponseAppointmentDTO(
                1L, 1L, 2L, 3L, request.startTime(), request.endTime(), Status.SCHEDULED
        );
        when(appointmentService.createAppointment(any())).thenReturn(response);

        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("SCHEDULED"));
    }

    @Test
    void updateAppointment() throws Exception {
        UpdateAppointmentDTO request = new UpdateAppointmentDTO(
                LocalDateTime.now(), LocalDateTime.now().plusHours(1), Status.CANCELED
        );
        ResponseAppointmentDTO response = new ResponseAppointmentDTO(
                1L, 1L, 2L, 3L, request.startTime(), request.endTime(), request.status()
        );
        when(appointmentService.updateAppointment(1L, request)).thenReturn(response);

        mockMvc.perform(put("/api/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELED"));
    }

    @Test
    void deleteAppointment() throws Exception {
        mockMvc.perform(delete("/api/appointments/1"))
                .andExpect(status().isNoContent());

        verify(appointmentService).deleteAppointment(1L);
    }

    @Test
    void getAppointmentsByDoctorAndDate() throws Exception {
        ResponseAppointmentDTO response = new ResponseAppointmentDTO(
                1L, 1L, 2L, 3L,
                LocalDateTime.of(2025, 4, 25, 10, 0),
                LocalDateTime.of(2025, 4, 25, 11, 0),
                Status.SCHEDULED
        );
        when(appointmentService.findAppointmentByDoctorId(2L, LocalDate.of(2025, 4, 25)))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/appointments")
                        .param("doctorId", "2")
                        .param("date", "2025-04-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].doctorId").value(2));
    }
}
