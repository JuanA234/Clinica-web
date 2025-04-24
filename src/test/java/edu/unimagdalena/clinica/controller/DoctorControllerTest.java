package edu.unimagdalena.clinica.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.clinica.dto.Doctor.CreateDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.UpdateDoctorDTO;
import edu.unimagdalena.clinica.service.interfaces.DoctorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(DoctorController.class)
@Import(DoctorControllerTest.MockConfig.class)
class DoctorControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private DoctorService doctorService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public DoctorService doctorService() {
            return Mockito.mock(DoctorService.class);
        }
    }

    @Test
    void getAllDoctors()throws Exception {
        when(doctorService.findAllDoctors()).thenReturn(List.of(new ResponseDoctorDTO(1L, null, null, null, null, null)));
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

    }


    @Test
    void getDoctorById() throws Exception {
        ResponseDoctorDTO response = new ResponseDoctorDTO(1L, "Juan Avendaño",
                "juanavendano@gmail.com",
                "cardiologia",
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(12));
        when(doctorService.findDoctorById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/doctors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void createDoctor() throws Exception {
        CreateDoctorDTO request = new CreateDoctorDTO("Juan Avendaño",
                "juanavendano@gmail.com",
                "cardiologia",
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(12));

        ResponseDoctorDTO response = new ResponseDoctorDTO(1L, "Juan Avendaño",
                "juanavendano@gmail.com",
                "cardiologia",
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(12));

        when(doctorService.createDoctor(any())).thenReturn(response);

        mockMvc.perform(post("/api/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fullname").value("Juan Avendaño"));

    }

    @Test
    void updateDoctor() throws Exception {
        UpdateDoctorDTO request = new UpdateDoctorDTO("Juan Avendaño",
                "juanavendano@gmail.com",
                "cardiologia",
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(12));

        ResponseDoctorDTO response = new ResponseDoctorDTO(1L, "Juan Avendaño",
                "juanavendano@gmail.com",
                "cardiologia",
                LocalTime.now().plusHours(1),
                LocalTime.now().plusHours(12));

        when(doctorService.updateDoctorById(1L,request)).thenReturn(response);

        mockMvc.perform(put("/api/doctors/1").
                contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialty").value("cardiologia"));

    }

    @Test
    void deleteDoctor() throws Exception {
        mockMvc.perform(delete("/api/doctors/1"))
                .andExpect(status().isNoContent());

        verify(doctorService).deleteDoctorById(1L);
    }

    @Test
    void getDoctorBySpecialty() throws Exception{
        ResponseDoctorDTO doctor = new ResponseDoctorDTO(1L, "Dr. Cardiologo", "cardio@mail.com", "Cardiología", null, null);
        when(doctorService.findDoctorBySpecialty("Cardiología")).thenReturn(doctor);

        mockMvc.perform(get("/api/doctors")
                        .param("specialty", "Cardiología"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("Dr. Cardiologo"))
                .andExpect(jsonPath("$.specialty").value("Cardiología"));
    }
}