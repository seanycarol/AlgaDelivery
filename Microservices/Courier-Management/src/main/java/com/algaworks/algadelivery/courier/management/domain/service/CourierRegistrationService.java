package com.algaworks.algadelivery.courier.management.domain.service;

import com.algaworks.algadelivery.courier.management.api.model.AssignedDeliveryOutput;
import com.algaworks.algadelivery.courier.management.api.model.CourierInput;
import com.algaworks.algadelivery.courier.management.api.model.CourierOutput;
import com.algaworks.algadelivery.courier.management.domain.exception.DomainException;
import com.algaworks.algadelivery.courier.management.domain.model.Courier;
import com.algaworks.algadelivery.courier.management.domain.repository.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourierRegistrationService {

    private final CourierRepository courierRepository;

    public CourierOutput create(CourierInput input) {
        Courier courier = Courier.brandNew(input.getName(), input.getPhone());
        return toDTO(courierRepository.saveAndFlush(courier));
    }

    public CourierOutput update(UUID courierId, CourierInput input) {
        Courier courier = courierRepository.findById(courierId)
                .orElseThrow(() -> new DomainException("Courier not found"));
        courier.setName(input.getName());
        courier.setPhone(input.getPhone());
        return toDTO(courierRepository.saveAndFlush(courier));
    }

    public Page<CourierOutput> findAll(Pageable pageable) {
        Page<Courier> result = courierRepository.findAll(pageable);
        return result.map(this::toDTO);
    }

    public CourierOutput findById(UUID courierId) {
        Courier courier = courierRepository.findById(courierId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return toDTO(courier);
    }

    private CourierOutput toDTO(Courier courier) {
        return CourierOutput.builder()
                .name(courier.getName())
                .phone(courier.getPhone())
                .fulfilledDeliveriesQuantity(courier.getFulfilledDeliveriesQuantity())
                .pendingDeliveriesQuantity(courier.getPendingDeliveriesQuantity())
                .lastFulFilledDeliveryAt(courier.getLastFulFilledDeliveryAt())
                .pendingDeliveries(courier.getPendingDeliveries().stream().map(d -> new AssignedDeliveryOutput(d.getId(), d.getAssignedAt())).toList())
                .build();
    }
}
