package edu.unimagdalena.clinica.service.interfaces;


import edu.unimagdalena.clinica.dto.Patient.CreatePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.UpdatePatientDTO;

import java.util.List;

public interface PatientService {

    ResponsePatientDTO createPatient(CreatePatientDTO request);

    List<ResponsePatientDTO> findAllPatients();

    ResponsePatientDTO findPatientById(Long id);

    ResponsePatientDTO updatePatientById(Long id, UpdatePatientDTO request);

    void deletePatiendById(Long id);
}
