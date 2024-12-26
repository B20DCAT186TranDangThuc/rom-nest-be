package com.dangthuc.newword.entities;


import com.dangthuc.newword.enums.Mouth;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "THUCTD_INVOICES")
@Getter
@Setter
public class Invoice extends BaseEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INVOICE_SEQ")
//    @SequenceGenerator(sequenceName = "INVOICE_SEQ", allocationSize = 1, name = "INVOICE_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String status;
    @Enumerated(EnumType.STRING)
    private Mouth mouth;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "tenant_id")
    private User tenant;
}
