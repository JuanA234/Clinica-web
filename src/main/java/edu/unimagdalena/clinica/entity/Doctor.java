package edu.unimagdalena.clinica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;

    @Email
    @Column(unique = true)
    private String email;

    private String specialty;

    private LocalTime availableFrom;

    private LocalTime availableTo;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;
}
