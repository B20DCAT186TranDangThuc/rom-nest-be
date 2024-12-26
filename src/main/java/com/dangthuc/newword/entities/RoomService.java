package com.dangthuc.newword.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "THUCTD_ROOM_SERVICES")
@Getter
@Setter
public class RoomService extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SERVICE_SEQ")
//    @SequenceGenerator(sequenceName = "SERVICE_SEQ", allocationSize = 1, name = "SERVICE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    private double priceFerUnit;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roomServices")
    @JsonIgnore
    private List<Room> rooms;

    public RoomService() {
    }

    public RoomService(String name, String unit, double priceFerUnit, String description) {
        this.name = name;
        this.unit = unit;
        this.priceFerUnit = priceFerUnit;
        this.description = description;
    }
}
