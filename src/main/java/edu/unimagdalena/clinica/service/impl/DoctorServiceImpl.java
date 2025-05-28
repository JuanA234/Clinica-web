package edu.unimagdalena.clinica.service.impl;

import edu.unimagdalena.clinica.dto.Doctor.CreateDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.ResponseDoctorDTO;
import edu.unimagdalena.clinica.dto.Doctor.UpdateDoctorDTO;
import edu.unimagdalena.clinica.entity.Doctor;
import edu.unimagdalena.clinica.entity.Role;
import edu.unimagdalena.clinica.entity.User;
import edu.unimagdalena.clinica.enumeration.RolesEnum;
import edu.unimagdalena.clinica.exception.UserAlreadyRegistered;
import edu.unimagdalena.clinica.exception.notFound.DoctorNotFoundException;
import edu.unimagdalena.clinica.exception.notFound.RoleNotFoundException;
import edu.unimagdalena.clinica.mapper.DoctorMapper;
import edu.unimagdalena.clinica.repository.DoctorRepository;
import edu.unimagdalena.clinica.repository.RoleRepository;
import edu.unimagdalena.clinica.repository.UserRepository;
import edu.unimagdalena.clinica.service.interfaces.DoctorService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;


    public DoctorServiceImpl(DoctorRepository doctorRepository, DoctorMapper doctorMapper, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.repository = doctorRepository;
        this.mapper = doctorMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseDoctorDTO createDoctor(CreateDoctorDTO request) {

        if(userRepository.existsByEmail(request.email())){
            throw new UserAlreadyRegistered("Usuario ya existe");
        }

        User user = new User();
        user.setUsername(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        Role role = roleRepository.findByRole(RolesEnum.DOCTOR)
                .orElseThrow(()->new RoleNotFoundException("No existe el rol"));

        user.setRoles(Set.of(role));
        userRepository.save(user);


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
                .orElseThrow(()-> new DoctorNotFoundException("No se encontro el doctor con el id: " + id));

    }

    @Override
    public ResponseDoctorDTO updateDoctorById(Long id, UpdateDoctorDTO request) {
        Doctor foundDoctor = repository.findById(id).
                orElseThrow(()-> new DoctorNotFoundException("No se encontro el doctor con el id: " + id));
        mapper.updateEntityFromDTO(request, foundDoctor);
        return mapper.toDTO(repository.save(foundDoctor));
    }

    @Override
    public void deleteDoctorById(Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("No se encontró el doctor con el id: " + id));

        // Buscar el usuario por el email del doctor
        User user = userRepository.findByEmail(doctor.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario asociado al doctor"));
        userRepository.delete(user); // Elimina el usuario si existe

        repository.delete(doctor);
    }

    @Override
    public ResponseDoctorDTO findDoctorBySpecialty(String specialty) {
        return repository.findBySpecialty(specialty)
                .map(mapper::toDTO)
                .orElseThrow(()-> new DoctorNotFoundException("No se encontro el doctor con la especialidad: " + specialty));
    }
}
