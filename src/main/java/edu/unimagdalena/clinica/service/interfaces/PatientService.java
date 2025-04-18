package edu.unimagdalena.clinica.service.interfaces;


import edu.unimagdalena.clinica.dto.Patient.RequestPatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;

import java.util.List;

public interface PatientService {

    ResponsePatientDTO createPatient(RequestPatientDTO request);

    List<ResponsePatientDTO> findAllPatients();

    ResponsePatientDTO findPatientById(Long id);

    ResponsePatientDTO updatePatientById(Long id, RequestPatientDTO request);

    void deletePatiendById(Long id);
}
