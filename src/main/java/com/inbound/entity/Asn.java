package com.inbound.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "asn")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long asnId;

    @Column(nullable = false, unique = true)
    private String shipmentNumber;

    private String supplierName;

    @OneToMany(mappedBy = "asn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sku> skus;
}
