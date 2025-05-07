package edu.unimagdalena.clinica.entity;

import edu.unimagdalena.clinica.enumeration.RolesEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RolesEnum role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
