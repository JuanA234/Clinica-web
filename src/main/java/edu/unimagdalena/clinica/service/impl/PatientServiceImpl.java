package edu.unimagdalena.clinica.service.impl;

import edu.unimagdalena.clinica.dto.Patient.RequestPatientDTO;
import edu.unimagdalena.clinica.dto.Patient.ResponsePatientDTO;
import edu.unimagdalena.clinica.entity.Patient;
import edu.unimagdalena.clinica.mapper.PatientMapper;
import edu.unimagdalena.clinica.repository.PatientRepository;
import edu.unimagdalena.clinica.service.interfaces.PatientService;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class PatientServiceImpl implements PatientService{

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;


    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public ResponsePatientDTO createPatient(RequestPatientDTO request) {
        return patientMapper.toDTO(patientRepository.save(patientMapper.toEntity(request)));
    }

    @Override
    public List<ResponsePatientDTO> findAllPatients() {
        return patientRepository.findAll().stream().map(patientMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public ResponsePatientDTO findPatientById(Long id) {
        return patientRepository.findById(id).map(patientMapper::toDTO).
                 orElseThrow(()-> new EntityNotFoundException("No se encontró el paciente"));
        //TODO Cambiar a excepción personalizada
    }

    @Override
    public ResponsePatientDTO updatePatientById(Long id, RequestPatientDTO request) {
        Patient foundPatient = patientRepository.findById(id).
                orElseThrow(()->new EntityNotFoundException("No se encontró el paciente"));
        patientMapper.updateEntityFromDTO(request, foundPatient);
        return patientMapper.toDTO(foundPatient);
    }

    @Override
    public void deletePatiendById(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("No se encontró el paciente");
        }
        patientRepository.deleteById(id);
        //TODO Cambiar a excepción personalizada
    }
}
