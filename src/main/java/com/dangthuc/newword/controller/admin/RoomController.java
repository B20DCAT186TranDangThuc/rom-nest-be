package com.dangthuc.newword.controller.admin;

import com.dangthuc.newword.dto.request.RoomForm;
import com.dangthuc.newword.dto.response.RestResponse;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.entities.Room;
import com.dangthuc.newword.exceptions.ExitsException;
import com.dangthuc.newword.service.RoomService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<RestResponse<Room>> createRoom(@RequestBody RoomForm form) {
        RestResponse<Room> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Room created successfully");
        response.setData(roomService.create(form));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/rooms")
    public ResponseEntity<RestResponse<Room>> updateRoom(@RequestBody RoomForm form) throws ExitsException {
        if (roomService.fetchRoomById(form.getId()) == null) {
            throw new ExitsException("Room doesn't exist with id = " + form.getId());
        }
        RestResponse<Room> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Room updated successfully");
        response.setData(roomService.update(form));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/rooms/{id}")
    public ResponseEntity<RestResponse<Void>> deleteRoom(@PathVariable Long id) throws ExitsException {
        if (roomService.fetchRoomById(id) == null) {
            throw new ExitsException("Room doesn't exist with id = " + id);
        }
        roomService.deleteRoomById(id);

        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Room deleted successfully");
        response.setData(null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/rooms")
    public ResponseEntity<RestResponse<ResultPaginationDTO>> getAllRooms(@Filter Specification<Room> spec, Pageable page) {
        RestResponse<ResultPaginationDTO> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All rooms found");
        response.setData(roomService.fetchAllRooms(spec, page));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<RestResponse<Room>> getRoomById(@PathVariable Long id) throws ExitsException {
        if (roomService.fetchRoomById(id) == null) {
            throw new ExitsException("Room doesn't exist with id = " + id);
        }
        RestResponse<Room> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Room found");
        response.setData(roomService.fetchRoomById(id));
        return ResponseEntity.ok(response);
    }
}
