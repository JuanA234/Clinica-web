package edu.unimagdalena.clinica.controller;

import edu.unimagdalena.clinica.dto.ConsultRoom.CreateConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.ResponseConsultRoomDTO;
import edu.unimagdalena.clinica.dto.ConsultRoom.UpdateConsultRoomDTO;
import edu.unimagdalena.clinica.service.interfaces.ConsultRoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/v1/rooms")
public class ConsultRoomController {

    private final ConsultRoomService consultRoomService;

    public ConsultRoomController(ConsultRoomService consultRoomService) {
        this.consultRoomService = consultRoomService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseConsultRoomDTO>> getAllRooms(){
        return ResponseEntity.ok(consultRoomService.findAllConsultRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseConsultRoomDTO> getRoomById(@PathVariable Long id){
        return ResponseEntity.ok(consultRoomService.findConsultRoomById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseConsultRoomDTO> createRoom(@RequestBody @Valid CreateConsultRoomDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(consultRoomService.createConsultRoom(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseConsultRoomDTO> updateRoom(@PathVariable Long id, @RequestBody UpdateConsultRoomDTO request){
        return ResponseEntity.ok(consultRoomService.updateConsultRoomById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id){
        consultRoomService.deleteConsultRoomById(id);
        return ResponseEntity.noContent().build();
    }

}
