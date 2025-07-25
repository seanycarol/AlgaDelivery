package com.algaworks.algadelivery.courier.management.api.model;

import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@AllArgsConstructor
public class AssignedDeliveryOutput {
    private UUID id;
    private OffsetDateTime assignedAt;
}
