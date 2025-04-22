package edu.unimagdalena.clinica.service.impl;

import edu.unimagdalena.clinica.dto.Patient.CreatePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.dto.Patient.UpdatePatientDTO;
import edu.unimagdalena.clinica.entity.Patient;
import edu.unimagdalena.clinica.exception.PatientNotFoundException;
import edu.unimagdalena.clinica.mapper.PatientMapper;
import edu.unimagdalena.clinica.repository.PatientRepository;
import edu.unimagdalena.clinica.service.interfaces.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;


    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public ResponsePatientDTO createPatient(CreatePatientDTO request) {
        return patientMapper.toDTO(patientRepository.save(patientMapper.toEntity(request)));
    }

    @Override
    public List<ResponsePatientDTO> findAllPatients() {
        return patientRepository.findAll().stream().map(patientMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponsePatientDTO findPatientById(Long id) {
        return patientRepository.findById(id).map(patientMapper::toDTO).
                 orElseThrow(()-> new PatientNotFoundException("No se encontro el paciente con el id: " + id));
    }

    @Override
    public ResponsePatientDTO updatePatientById(Long id, UpdatePatientDTO request) {
        Patient foundPatient = patientRepository.findById(id).
                orElseThrow(()->new PatientNotFoundException("No se encontro el paciente con el id: " + id));
        patientMapper.updateEntityFromDTO(request, foundPatient);
        return patientMapper.toDTO(foundPatient);
    }

    @Override
    public void deletePatiendById(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new PatientNotFoundException("No se encontro el paciente con el id: " + id);
        }
        patientRepository.deleteById(id);
    }
}
