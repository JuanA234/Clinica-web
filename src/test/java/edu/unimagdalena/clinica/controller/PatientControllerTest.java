package edu.unimagdalena.clinica.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.unimagdalena.clinica.dto.Patient.CreatePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.UpdatePatientDTO;
import edu.unimagdalena.clinica.service.interfaces.PatientService;
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

@WebMvcTest(PatientController.class)
@Import(PatientControllerTest.MockConfig.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private PatientService patientService;



    @TestConfiguration
    static class MockConfig {
        @Bean
        public PatientService patientService() {
            return Mockito.mock(PatientService.class);
        }
    }

    @Test
    void getAllPatients() throws Exception {
        when(patientService.findAllPatients()).thenReturn(List.of(new ResponsePatientDTO(1L,
                null, null, null)));

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getPatientById() throws Exception {
        ResponsePatientDTO response = new ResponsePatientDTO(1L, "Juan Avendaño", null, null);
        when(patientService.findPatientById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createPatient() throws Exception {
        CreatePatientDTO request = new CreatePatientDTO("Juan Avendaño", "juan@gmail.com", null);

        ResponsePatientDTO response = new ResponsePatientDTO(1L, "Juan Avendaño", "juan@gmail.com", null);

        when(patientService.createPatient(any())).thenReturn(response);

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("juan@gmail.com"));
    }

    @Test
    void updatePatient() throws Exception {

        UpdatePatientDTO request = new UpdatePatientDTO("Juan Avendaño", "juanavendano@gmail.com",null);

        ResponsePatientDTO response = new ResponsePatientDTO(1L, "Juan Avendaño","juanavendano@gmail.com", null);

        when(patientService.updatePatientById(1L,request)).thenReturn(response);

        mockMvc.perform(put("/api/patients/1").
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juanavendano@gmail.com"));
    }

    @Test
    void deletePatient() throws Exception {
        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isNoContent());

        verify(patientService).deletePatiendById(1L);
    }
}