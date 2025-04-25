package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.ConsultRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsultRoomRepository extends JpaRepository<ConsultRoom, Long> {

    Optional<ConsultRoom> findById(Long id);

    boolean existsById(Long id);

}
