package edu.unimagdalena.clinica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Data
@Builder
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
}
