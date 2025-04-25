package edu.unimagdalena.clinica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.clinica.dto.MedicalRecord.CreateMedicalRecordDTO;
import edu.unimagdalena.clinica.dto.MedicalRecord.ResponseMedicalRecordDTO;
import edu.unimagdalena.clinica.service.interfaces.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalRecordController.class)
@Import(MedicalRecordControllerTest.MockConfig.class)
class MedicalRecordControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private MedicalRecordService medicalRecordService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public MedicalRecordService medicalRecordService() {
            return Mockito.mock(MedicalRecordService.class);
        }
    }

    @Test
    void getAllRecords() throws Exception {
        ResponseMedicalRecordDTO response = new ResponseMedicalRecordDTO(
                1L, 10L, 20L, "Diagnosis example", "Notes", LocalDateTime.now()
        );
        when(medicalRecordService.findAllRecords()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/records"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getRecordById() throws Exception {
        ResponseMedicalRecordDTO response = new ResponseMedicalRecordDTO(
                1L, 10L, 20L, "Diagnosis example", "Notes", LocalDateTime.now()
        );
        when(medicalRecordService.findRecordById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/records/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void getRecordsByPatientId() throws Exception {
        ResponseMedicalRecordDTO response = new ResponseMedicalRecordDTO(
                1L, 10L, 20L, "Diagnosis example", "Notes", LocalDateTime.now()
        );
        when(medicalRecordService.findRecordsByPatientId(20L)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/records/patients/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patientId").value(20));
    }

    @Test
    void createRecord() throws Exception {
        CreateMedicalRecordDTO request = new CreateMedicalRecordDTO(
                10L, 20L, "Diagnosis example", "Notes", LocalDateTime.now()
        );
        ResponseMedicalRecordDTO response = new ResponseMedicalRecordDTO(
                1L, 10L, 20L, "Diagnosis example", "Notes", request.createdAt()
        );
        when(medicalRecordService.createRecord(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/records")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.diagnosis").value("Diagnosis example"));
    }

    @Test
    void deleteRecord() throws Exception {
        mockMvc.perform(delete("/api/v1/records/1"))
                .andExpect(status().isNoContent());

        verify(medicalRecordService).deleteById(1L);
    }
}
