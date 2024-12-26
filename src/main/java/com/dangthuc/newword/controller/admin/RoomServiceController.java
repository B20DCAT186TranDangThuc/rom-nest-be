package com.dangthuc.newword.controller.admin;

import com.dangthuc.newword.dto.response.RestResponse;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.entities.RoomService;
import com.dangthuc.newword.exceptions.ExitsException;
import com.dangthuc.newword.service.RoomServicesService;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class RoomServiceController {

    @Autowired
    private RoomServicesService roomServicesService;

    @PostMapping("/room_services")
    public ResponseEntity<RestResponse<RoomService>> addRoomService(@RequestBody RoomService roomService) {
        RestResponse<RoomService> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Room service added successfully");
        response.setData(roomServicesService.save(roomService));

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PutMapping("/room_services")
    public ResponseEntity<RestResponse<RoomService>> updateRoomService(@RequestBody RoomService roomService) throws ExitsException {
        if (roomServicesService.findById(roomService.getId()) == null) {
            throw new ExitsException("Room service doesn't exist by id = " + roomService.getId());
        }
        RestResponse<RoomService> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Room service updated successfully");
        response.setData(roomServicesService.save(roomService));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/room_services/{id}")
    public ResponseEntity<RestResponse<Void>> deleteRoomService(@PathVariable Long id) throws ExitsException {
        if (roomServicesService.findById(id) == null) {
            throw new ExitsException("Room service doesn't exist by id = " + id);
        }
        roomServicesService.delete(id);
        RestResponse<Void> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Room service deleted successfully");
        response.setData(null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/room_services")
    public ResponseEntity<RestResponse<ResultPaginationDTO>> getAllRoomServices(@Filter Specification<RoomService> spec, Pageable page) {
        RestResponse<ResultPaginationDTO> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All room services found");
        response.setData(roomServicesService.findAll(spec, page));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/room_services/{id}")
    public ResponseEntity<RestResponse<RoomService>> getRoomService(@PathVariable Long id) throws ExitsException {
        RoomService roomService = roomServicesService.findById(id);
        if (roomService == null) {
            throw new ExitsException("Room service doesn't exist by id = " + id);
        }
        RestResponse<RoomService> response = new RestResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Room service found");
        response.setData(roomService);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
