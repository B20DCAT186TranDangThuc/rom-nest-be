package com.dangthuc.newword.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomForm {

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
    private String description;
    private List<Long> listServices;
}
