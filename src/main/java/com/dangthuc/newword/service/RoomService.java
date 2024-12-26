package com.dangthuc.newword.service;

import com.dangthuc.newword.dto.request.RoomForm;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.entities.Room;
import com.dangthuc.newword.repository.RoomRepository;
import com.dangthuc.newword.repository.RoomServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomServiceRepository roomServiceRepository;

    public Room create(RoomForm form) {
        try {
            List<com.dangthuc.newword.entities.RoomService> services = roomServiceRepository.findAllById(form.getListServices());
            Room room = new Room(form);
            room.setRoomServices(services);

            return roomRepository.save(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Room update(RoomForm form) {
        Room currentRoom = fetchRoomById(form.getId());
        if (currentRoom == null) {
            return null;
        }
        currentRoom.setNumber(form.getNumber());
        currentRoom.setFloor(form.getFloor());
        currentRoom.setName(form.getName());
        currentRoom.setType(form.getType());
        currentRoom.setArea(form.getArea());
        currentRoom.setPrice(form.getPrice());
        currentRoom.setStatus(form.isStatus());
        currentRoom.setMaxOccupancy(form.getMaxOccupancy());
        currentRoom.setCurrentOccupancy(form.getCurrentOccupancy());
        currentRoom.setDescription(form.getDescription());
        currentRoom.setRoomServices(roomServiceRepository.findAllById(form.getListServices()));
        return roomRepository.save(currentRoom);
    }

    public Room fetchRoomById(Long id) {
        return roomRepository.findById(id).orElse(null);
    }

    public ResultPaginationDTO fetchAllRooms(Specification<Room> spec, Pageable page) {
        return PaginationService.handlePagination(spec, page, roomRepository);
    }

    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }
}
