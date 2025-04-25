package edu.unimagdalena.clinica.service.impl;

import edu.unimagdalena.clinica.dto.MedicalRecord.CreateMedicalRecordDTO;
import edu.unimagdalena.clinica.dto.MedicalRecord.ResponseMedicalRecordDTO;
import edu.unimagdalena.clinica.entity.Appointment;
import edu.unimagdalena.clinica.entity.MedicalRecord;
import edu.unimagdalena.clinica.entity.Patient;
import edu.unimagdalena.clinica.entity.Status;
import edu.unimagdalena.clinica.exception.AppointmentNotFoundException;
import edu.unimagdalena.clinica.exception.AppointmentTimeNotAvailableException;
import edu.unimagdalena.clinica.exception.MedicalRecordNotFoundException;
import edu.unimagdalena.clinica.exception.PatientNotFoundException;
import edu.unimagdalena.clinica.mapper.MedicalRecordMapper;
import edu.unimagdalena.clinica.repository.AppointmentRepository;
import edu.unimagdalena.clinica.repository.MedicalRecordRepository;
import edu.unimagdalena.clinica.repository.PatientRepository;
import edu.unimagdalena.clinica.service.interfaces.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;


    @Override
    public ResponseMedicalRecordDTO createRecord(CreateMedicalRecordDTO request) {

        Appointment appointment = appointmentRepository.findById(request.appointmentId())
                .orElseThrow(() -> new AppointmentNotFoundException("No se encontró la cita con id " + request.appointmentId()));
        if (appointment.getStatus() != Status.COMPLETED){
            throw new AppointmentTimeNotAvailableException("La cita aún no fue completada");
        }

        Patient patient = patientRepository.findById(request.patientId()).
                orElseThrow(()->new PatientNotFoundException("Paciente con id: "+request.patientId() + " no encontrado"));

        MedicalRecord medicalRecord = medicalRecordMapper.toEntity(request);
        medicalRecord.setAppointment(appointment);
        medicalRecord.setPatient(patient);

        return medicalRecordMapper.toDTO(medicalRecordRepository.save(medicalRecord));
    }

    @Override
    public ResponseMedicalRecordDTO findRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .map(medicalRecordMapper::toDTO)
                .orElseThrow(() -> new MedicalRecordNotFoundException("No se encontró el historial con el id " + id));
    }

    @Override
    public List<ResponseMedicalRecordDTO> findRecordsByPatientId(Long id) {
        return medicalRecordRepository.findByPatientId(id).stream()
                .map(medicalRecordMapper::toDTO).toList();
    }

    @Override
    public List<ResponseMedicalRecordDTO> findAllRecords() {
        return medicalRecordRepository.findAll().stream()
                .map(medicalRecordMapper::toDTO).toList();
    }

    @Override
    public void deleteById(Long id) {
        if (medicalRecordRepository.findById(id).isEmpty()){
            throw new MedicalRecordNotFoundException("No se encontró el historial con el id " + id);
        }
        medicalRecordRepository.deleteById(id);
    }
}
