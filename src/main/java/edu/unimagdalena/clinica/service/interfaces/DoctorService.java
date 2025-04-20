package edu.unimagdalena.clinica.service.interfaces;

import edu.unimagdalena.clinica.dto.Doctor.CreateDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.UpdateDoctorDTO;

import java.util.List;

public interface DoctorService {

    ResponseDoctorDTO createDoctor(CreateDoctorDTO request);

    List<ResponseDoctorDTO> findAllDoctors();

    ResponseDoctorDTO findDoctorById(Long id);

    ResponseDoctorDTO updateDoctorById(Long id, UpdateDoctorDTO request);

    void deleteDoctorById(Long id);
}
