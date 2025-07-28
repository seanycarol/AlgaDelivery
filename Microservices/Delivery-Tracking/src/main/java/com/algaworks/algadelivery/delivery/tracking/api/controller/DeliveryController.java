package com.algaworks.algadelivery.delivery.tracking.api.controller;

import com.algaworks.algadelivery.delivery.tracking.api.model.CourierIdInput;
import com.algaworks.algadelivery.delivery.tracking.api.model.DeliveryInput;
import com.algaworks.algadelivery.delivery.tracking.api.model.DeliveryOutput;
import com.algaworks.algadelivery.delivery.tracking.domain.service.DeliveryCheckpointService;
import com.algaworks.algadelivery.delivery.tracking.domain.service.DeliveryPreparationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryPreparationService deliveryPreparationService;
    private final DeliveryCheckpointService deliveryCheckpointService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryOutput draft(@RequestBody @Valid DeliveryInput input) {
        return deliveryPreparationService.draft(input);
    }

    @PutMapping("/{deliveryId}")
    public DeliveryOutput edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput input) {
        return deliveryPreparationService.edit(deliveryId, input);
    }

    @GetMapping
    public PagedModel<DeliveryOutput> findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(deliveryPreparationService.findAll(pageable));
    }

    @SneakyThrows
    @GetMapping("/{deliveryId}")
    public DeliveryOutput findById(@PathVariable UUID deliveryId) {
        if(Math.random() < 0.7) {
            throw new RuntimeException();
        }
        int millis = new Random().nextInt(400);
        Thread.sleep(millis);
        return deliveryPreparationService.findById(deliveryId);
    }

    @PostMapping("/{deliveryId}/placement")
    public void place(@PathVariable UUID deliveryId) {
        deliveryCheckpointService.place(deliveryId);
    }

    @PostMapping("/{deliveryId}/pickups")
    public void pickup(@PathVariable UUID deliveryId, @RequestBody @Valid CourierIdInput input) {
        deliveryCheckpointService.pickup(deliveryId, input.getCourierId());
    }

    @PostMapping("/{deliveryId}/completion")
    public void complete(@PathVariable UUID deliveryId) {
        deliveryCheckpointService.complete(deliveryId);
    }
}
