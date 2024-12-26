package com.dangthuc.newword.repository;

import com.dangthuc.newword.entities.RoomService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomServiceRepository extends JpaRepository<RoomService, Long>, JpaSpecificationExecutor<RoomService> {
}
