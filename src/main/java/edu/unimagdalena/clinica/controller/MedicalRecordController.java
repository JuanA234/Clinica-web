package edu.unimagdalena.clinica.controller;

import edu.unimagdalena.clinica.dto.MedicalRecord.CreateMedicalRecordDTO;
import edu.unimagdalena.clinica.dto.MedicalRecord.ResponseMedicalRecordDTO;
import edu.unimagdalena.clinica.service.interfaces.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseMedicalRecordDTO>> getAllRecords(){
        return ResponseEntity.ok(medicalRecordService.findAllRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMedicalRecordDTO> getRecordById(@PathVariable Long id){
        return ResponseEntity.ok(medicalRecordService.findRecordById(id));
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<List<ResponseMedicalRecordDTO>> getRecordByPatientId(@PathVariable Long patientId){
        return ResponseEntity.ok(medicalRecordService.findRecordsByPatientId(patientId));
    }

    @PostMapping
    public ResponseEntity<ResponseMedicalRecordDTO> createRecord(@RequestBody @Valid CreateMedicalRecordDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecordService.createRecord(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id){
        medicalRecordService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
