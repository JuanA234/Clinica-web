package edu.unimagdalena.clinica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consult_rooms")
public class ConsultRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String floor;

    private String description;

    @OneToMany(mappedBy = "consultRoom")
    private Set<Appointment> appointments;
}
