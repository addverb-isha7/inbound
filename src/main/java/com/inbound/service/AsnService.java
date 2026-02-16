package com.inbound.service;

import com.inbound.client.InventoryClient;
import com.inbound.dto.VerifyRequest;
import com.inbound.entity.Asn;
import com.inbound.entity.Sku;
import com.inbound.repository.AsnRepository;
import com.inbound.repository.SkuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AsnService {

    private final AsnRepository asnRepository;
    private final SkuRepository skuRepository;
    private final InventoryClient inventoryClient;

    public Asn createAsn(Asn asn) {

        asn.getSkus().forEach(sku -> {
            sku.setStatus("PENDING");
            sku.setReceivedQuantity(0);
            sku.setAsn(asn);
        });

        return asnRepository.save(asn);
    }

    public List<Asn> getAll() {
        return asnRepository.findAll();
    }

    public Asn getByShipment(String shipmentNumber) {
        return asnRepository.findByShipmentNumber(shipmentNumber)
                .orElseThrow(() -> new RuntimeException("ASN not found"));
    }

    public String verify(String shipmentNumber, VerifyRequest request) {

        Asn asn = getByShipment(shipmentNumber);

        Sku sku = skuRepository
                .findByAsnAndSkuName(asn, request.getSkuName())
                .orElseThrow(() -> new RuntimeException("SKU not found"));

        boolean fullMatch =
                sku.getBatchNumber().equals(request.getBatchNumber()) &&
                        sku.getMrp().equals(request.getMrp()) &&
                        sku.getExpiry().equals(request.getExpiry());

        if (fullMatch) {
            sku.setStatus("GOOD");
        } else {
            sku.setStatus("BAD");
        }

        skuRepository.save(sku);
// Call inventory service
// inventoryClient.addToInventory(request);
        return "Verification Completed. Status: " + sku.getStatus();
    }

}

