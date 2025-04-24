package edu.unimagdalena.clinica.controller;

import edu.unimagdalena.clinica.dto.Doctor.CreateDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.UpdateDoctorDTO;
import edu.unimagdalena.clinica.service.interfaces.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseDoctorDTO>> getAllDoctors(){
        return ResponseEntity.ok(doctorService.findAllDoctors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDoctorDTO> getDoctorById(@PathVariable Long id){
        return ResponseEntity.ok(doctorService.findDoctorById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDoctorDTO> createDoctor(@RequestBody CreateDoctorDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody UpdateDoctorDTO request){
        return ResponseEntity.ok(doctorService.updateDoctorById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id){
        doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }

}
