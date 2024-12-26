package com.dangthuc.newword.entities;

import com.dangthuc.newword.dto.request.RoomForm;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "THUCTD_ROOMS")
@Getter
@Setter
public class Room extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROOM_SEQ")
//    @SequenceGenerator(sequenceName = "ROOM_SEQ", allocationSize = 1, name = "ROOM_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int number;
    private int floor;
    private String name;
    private String type;
    private double area;
    private double price;
    private boolean status;
    private int maxOccupancy;
    private int currentOccupancy;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    List<User> users;

    @ManyToMany
    @JsonIgnoreProperties(value = {"rooms"})
    @JoinTable(name = "THUCTD_ROOM_SERVICE_USAGE", joinColumns = @JoinColumn(name = "room_id"), inverseJoinColumns = @JoinColumn(name = "room_service_id"))
    List<RoomService> roomServices;

    public Room() {
    }

    public Room(RoomForm roomForm) {
        this.number = roomForm.getNumber();
        this.floor = roomForm.getFloor();
        this.name = roomForm.getName();
        this.type = roomForm.getType();
        this.area = roomForm.getArea();
        this.price = roomForm.getPrice();
        this.status = roomForm.isStatus();
        this.maxOccupancy = roomForm.getMaxOccupancy();
        this.currentOccupancy = roomForm.getCurrentOccupancy();
        this.description = roomForm.getDescription();

    }
}
