package com.algaworks.algadelivery.courier.management.api.controller;

import com.algaworks.algadelivery.courier.management.api.model.CourierInput;
import com.algaworks.algadelivery.courier.management.api.model.CourierOutput;
import com.algaworks.algadelivery.courier.management.api.model.CourierPayoutCalculationInput;
import com.algaworks.algadelivery.courier.management.api.model.CourierPayoutResultModel;
import com.algaworks.algadelivery.courier.management.domain.service.CourierRegistrationService;
import com.algaworks.algadelivery.courier.management.domain.service.CourierPayoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
public class CourierController {

    private final CourierRegistrationService courierRegistrationService;
    private final CourierPayoutService courierPayoutService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourierOutput create(@RequestBody @Valid CourierInput input) {
        return courierRegistrationService.create(input);
    }

    @PutMapping("/{courierId}")
    public CourierOutput update(@PathVariable UUID courierId, @RequestBody @Valid CourierInput input) {
        return courierRegistrationService.update(courierId, input);
    }

    @GetMapping
    public PagedModel<CourierOutput> findAll(@PageableDefault Pageable pageable) {
        return new PagedModel<>(courierRegistrationService.findAll(pageable));
    }

    @GetMapping("/{courierId}")
    public CourierOutput findById(@PathVariable UUID courierId) {
        return courierRegistrationService.findById(courierId);
    }

    @PostMapping("/payout-calculation")
    public CourierPayoutResultModel calculate(@RequestBody CourierPayoutCalculationInput input) {
        BigDecimal payoutFee = courierPayoutService.calculate(input.getDistanceInKm());
        return new CourierPayoutResultModel(payoutFee);
    }
}
