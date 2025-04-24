package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findById(Long id);
    boolean existsById(Long id);


    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
            "AND a.startTime >= :startOfDay  " +
            "AND a.startTime < :endOfDay")
    List<Appointment> findByDoctorAndDate(@Param("doctorId") Long doctorId, @Param("startOfDay") LocalDateTime startOfDay,
                                       @Param("endOfDay") LocalDateTime endOfDay);


    @Query("SELECT a FROM Appointment a WHERE ((a.doctor.id = :doctorId OR a.consultRoom.id = :consultRoomId))"+
    "AND ((a.startTime < :endTime AND a.endTime > :startTime))")
    List<Appointment> findConflicts(@Param("doctorId") Long doctorId, @Param("consultRoomId") Long consultRoomId,
                                    @Param("startTime") LocalDateTime startTime,
                                    @Param("endTime") LocalDateTime endTime);


}
