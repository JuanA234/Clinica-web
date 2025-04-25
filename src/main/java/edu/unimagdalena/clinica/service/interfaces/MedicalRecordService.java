package edu.unimagdalena.clinica.service.interfaces;

import edu.unimagdalena.clinica.dto.MedicalRecord.CreateMedicalRecordDTO;
import edu.unimagdalena.clinica.dto.MedicalRecord.ResponseMedicalRecordDTO;

import java.util.List;

public interface MedicalRecordService {

    ResponseMedicalRecordDTO createRecord(CreateMedicalRecordDTO request);

    ResponseMedicalRecordDTO findRecordById(Long id);

    List<ResponseMedicalRecordDTO> findRecordsByPatientId(Long id);

    List<ResponseMedicalRecordDTO> findAllRecords();

    void deleteById(Long id);
}
