package edu.unimagdalena.clinica.controller;

import edu.unimagdalena.clinica.dto.Patient.CreatePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.UpdatePatientDTO;
import edu.unimagdalena.clinica.service.interfaces.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {this.patientService = patientService;}

    @GetMapping
    public ResponseEntity<List<ResponsePatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePatientDTO> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.findPatientById(id));
    }

    @PostMapping
    public ResponseEntity<ResponsePatientDTO> createPatient(@RequestBody @Valid CreatePatientDTO createPatientDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(patientService.createPatient(createPatientDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePatientDTO> updatePatient(@PathVariable Long id, @RequestBody UpdatePatientDTO updatePatientDTO) {
        return ResponseEntity.ok(patientService.updatePatientById(id, updatePatientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatiendById(id);
        return ResponseEntity.noContent().build();
    }


}
