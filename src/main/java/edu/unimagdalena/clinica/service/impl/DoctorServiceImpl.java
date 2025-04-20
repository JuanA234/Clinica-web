package edu.unimagdalena.clinica.service.impl;

import edu.unimagdalena.clinica.dto.Doctor.CreateDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.UpdateDoctorDTO;
import edu.unimagdalena.clinica.entity.Doctor;
import edu.unimagdalena.clinica.mapper.DoctorMapper;
import edu.unimagdalena.clinica.repository.DoctorRepository;
import edu.unimagdalena.clinica.service.interfaces.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;


    public DoctorServiceImpl(DoctorRepository repository, DoctorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ResponseDoctorDTO createDoctor(CreateDoctorDTO request) {
        return mapper.toDTO(repository.save(mapper.toEntity(request)));
    }

    @Override
    public List<ResponseDoctorDTO> findAllDoctors() {
        return repository.findAll().stream()
                .map(mapper::toDTO).toList();
    }

    @Override
    public ResponseDoctorDTO findDoctorById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(()-> new EntityNotFoundException("No se encontró el doctor"));
        //TODO Cambiar a excepción personalizada
    }

    @Override
    public ResponseDoctorDTO updateDoctorById(Long id, UpdateDoctorDTO request) {
        Doctor foundDoctor = repository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("No se encontró el doctor"));
        mapper.updateEntityFromDTO(request, foundDoctor);
        return mapper.toDTO(repository.save(foundDoctor));
        //TODO Cambiar a excepción personalizada
    }

    @Override
    public void deleteDoctorById(Long id) {
        if (!repository.existsById(id)){
            throw new EntityNotFoundException("No se encontró el doctor");
        }
        repository.deleteById(id);
        //TODO Cambiar a excepción personalizada
    }
}
