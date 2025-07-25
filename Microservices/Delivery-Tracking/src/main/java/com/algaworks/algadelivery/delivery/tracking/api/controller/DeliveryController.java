package com.algaworks.algadelivery.delivery.tracking.api.controller;

import com.algaworks.algadelivery.delivery.tracking.api.model.DeliveryInput;
import com.algaworks.algadelivery.delivery.tracking.api.model.DeliveryOutput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryOutput draft(@RequestBody @Valid DeliveryInput input) {
        return null;
    }

    @PutMapping("/{deliveryId}")
    public DeliveryOutput edit(@PathVariable UUID deliveryId, @RequestBody @Valid DeliveryInput input) {
        return null;
    }
}
