package edu.unimagdalena.clinica.repository;

import edu.unimagdalena.clinica.entity.ConsultRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.TestcontainersConfiguration;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@Import(TestcontainersConfiguration.class)
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConsultRoomRepositoryTest {

    @Autowired
    ConsultRoomRepository consultRoomRepository;

    ConsultRoom consultRoom;


    @BeforeEach
    void setUp() {
        consultRoom = new ConsultRoom(null, "consultorio 1", "piso 1", "");
        consultRoomRepository.save(consultRoom);
    }

    @Test
    void findById() {
        Optional<ConsultRoom> consultRoomEncontrado = consultRoomRepository.findById(consultRoom.getId());

        assertTrue(consultRoomEncontrado.isPresent());
        assertEquals(consultRoom.getId(), consultRoomEncontrado.get().getId());
    }

    @Test
    void existsById() {
        assertTrue(consultRoomRepository.existsById(consultRoom.getId()));
    }
}