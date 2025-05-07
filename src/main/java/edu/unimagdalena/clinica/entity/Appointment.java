package edu.unimagdalena.clinica.entity;

import edu.unimagdalena.clinica.enumeration.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "patient_id",referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "consult_room_id", nullable = false)
    private ConsultRoom consultRoom;

    @Future
    private LocalDateTime startTime;

    @Future
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "appointment")
    private MedicalRecord medicalRecord;
}
