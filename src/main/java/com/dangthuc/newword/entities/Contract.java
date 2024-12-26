package com.dangthuc.newword.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Contract extends BaseEntity {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private double totalPrice;
    private String terms;
}
