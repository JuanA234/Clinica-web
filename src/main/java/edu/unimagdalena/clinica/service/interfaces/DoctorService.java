package edu.unimagdalena.clinica.service.interfaces;

import edu.unimagdalena.clinica.dto.Doctor.RequestDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.entity.Doctor;

import java.util.List;

public interface DoctorService {

    ResponseDoctorDTO createDoctor(RequestDoctorDTO request);

    List<ResponseDoctorDTO> findAllDoctors();

    ResponseDoctorDTO findDoctorById(Long id);

    ResponseDoctorDTO updateDoctorById(Long id, RequestDoctorDTO request);

    void deleteDoctorById(Long id);
}
