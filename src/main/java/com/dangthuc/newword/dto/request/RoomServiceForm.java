package com.dangthuc.newword.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RoomServiceForm {
    private Long id;
    private String name;
    private String unit;
    private double priceFerUnit;
    private String description;
}
