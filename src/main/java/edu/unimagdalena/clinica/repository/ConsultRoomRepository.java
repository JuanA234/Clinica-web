package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.ConsultRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {
    Optional<ConsultRoom> findById(Long id);
    boolean existsById(Long id);
}
