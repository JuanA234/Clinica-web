package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    Optional<MedicalRecord> findById(Long id);

    List<MedicalRecord> findByPatientId(Long id);

}
