package com.dangthuc.newword.service;

import com.dangthuc.newword.dto.request.RoomServiceForm;
import com.dangthuc.newword.dto.response.ResultPaginationDTO;
import com.dangthuc.newword.entities.RoomService;
import com.dangthuc.newword.exceptions.ExitsException;
import com.dangthuc.newword.repository.RoomServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServicesService {

    private final RoomServiceRepository roomServiceRepository;

    public RoomService save(RoomService request) {
        if (request.getId() == null) {
            return roomServiceRepository.save(request);
        }
        RoomService currentRoomService = roomServiceRepository.findById(request.getId()).orElse(null);
        if (currentRoomService != null) {
            currentRoomService.setName(request.getName());
            currentRoomService.setUnit(request.getUnit());
            currentRoomService.setPriceFerUnit(request.getPriceFerUnit());
            currentRoomService.setDescription(request.getDescription());
            return roomServiceRepository.save(currentRoomService);
        }
        return null;
    }

    public RoomService findById(Long id) {
        return roomServiceRepository.findById(id).orElse(null);
    }

    public ResultPaginationDTO findAll(Specification<RoomService> specification, Pageable pageable) {
        return PaginationService.handlePagination(specification, pageable, roomServiceRepository);
    }

    public void delete(Long id) throws ExitsException {
        RoomService currentRoomService = roomServiceRepository.findById(id).orElse(null);
        if (currentRoomService != null) {
            currentRoomService.getRooms().forEach(room -> room.getRoomServices().remove(currentRoomService));
            roomServiceRepository.delete(currentRoomService);
        } else {
            throw new ExitsException("Room service doesn't exits by id = " + id);
        }

    }
}
