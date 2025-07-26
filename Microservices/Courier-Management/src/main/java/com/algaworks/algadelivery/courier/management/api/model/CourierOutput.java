package com.algaworks.algadelivery.courier.management.api.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CourierOutput {
    private UUID id;
    private String name;
    private String phone;
    private Integer fulfilledDeliveriesQuantity;
    private Integer pendingDeliveriesQuantity;
    private OffsetDateTime lastFulFilledDeliveryAt;
    private List<AssignedDeliveryOutput> pendingDeliveries;
}
