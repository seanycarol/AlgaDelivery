package com.algaworks.algadelivery.delivery.tracking.api.model;

import com.algaworks.algadelivery.delivery.tracking.domain.model.DeliveryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class DeliveryOutput {
    private UUID id;
    private UUID courierId;

    private DeliveryStatus status;

    private OffsetDateTime placeAt;
    private OffsetDateTime assignedAt;
    private OffsetDateTime expectedDeliveryAt;
    private OffsetDateTime fulfilledAt;

    private BigDecimal distanceFee;
    private BigDecimal courierPayout;
    private BigDecimal totalCost;

    private Integer totalItems;

    private ContactPointInput sender;
    private ContactPointInput recipient;

    private List<ItemInput> items;
}
