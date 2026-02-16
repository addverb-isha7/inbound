package com.inbound.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sku")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skuId;

    private String status;   // GOOD / DAMAGED

    private Double mrp;

    private String batch;

    private LocalDate expiry;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "asn_id")
    private Asn asn;
}

