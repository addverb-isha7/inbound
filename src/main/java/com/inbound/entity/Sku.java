package com.inbound.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String skuName;
    private Double mrp;
    private String batchNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiry;

    private Integer expectedQuantity;
    private Integer receivedQuantity;
    private String status; // PENDING / GOOD / DAMAGED

    @ManyToOne
    @JoinColumn(name = "asn_id")
    private Asn asn;
}

