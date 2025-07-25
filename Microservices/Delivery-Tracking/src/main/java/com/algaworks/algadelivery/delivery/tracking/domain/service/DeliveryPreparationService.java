package com.algaworks.algadelivery.delivery.tracking.domain.service;

import com.algaworks.algadelivery.delivery.tracking.api.model.ContactPointInput;
import com.algaworks.algadelivery.delivery.tracking.api.model.DeliveryInput;
import com.algaworks.algadelivery.delivery.tracking.api.model.DeliveryOutput;
import com.algaworks.algadelivery.delivery.tracking.api.model.ItemInput;
import com.algaworks.algadelivery.delivery.tracking.domain.exception.DomainException;
import com.algaworks.algadelivery.delivery.tracking.domain.model.ContactPoint;
import com.algaworks.algadelivery.delivery.tracking.domain.model.Delivery;
import com.algaworks.algadelivery.delivery.tracking.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeliveryPreparationService {

    private final DeliveryRepository deliveryRepository;

    @Transactional
    public DeliveryOutput draft(DeliveryInput input) {
        Delivery delivery = Delivery.draft();
        handlePreparation(input, delivery);
        return toDTO(deliveryRepository.saveAndFlush(delivery));
    }

    @Transactional
    public DeliveryOutput edit(UUID deliveryId, DeliveryInput input) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DomainException("Delivery not found"));
        delivery.removeItems();
        handlePreparation(input, delivery);
        return toDTO(deliveryRepository.saveAndFlush(delivery));
    }

    private void handlePreparation(DeliveryInput input, Delivery delivery) {
        ContactPointInput senderInput = input.getSender();
        ContactPointInput recipientInput = input.getRecipient();

        ContactPoint sender = ContactPoint.builder()
                .zipCode(senderInput.getZipCode())
                .street(senderInput.getStreet())
                .number(senderInput.getNumber())
                .complement(senderInput.getComplement())
                .name(senderInput.getName())
                .phone(senderInput.getNumber())
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode(recipientInput.getZipCode())
                .street(recipientInput.getStreet())
                .number(recipientInput.getNumber())
                .complement(recipientInput.getComplement())
                .name(recipientInput.getName())
                .phone(recipientInput.getNumber())
                .build();

        Duration expectedDeliveryTime = Duration.ofHours(3);
        BigDecimal distanceFee = new BigDecimal("10");

        BigDecimal payout = new BigDecimal("10");

        var preparationDetails = Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(distanceFee)
                .courierPayout(payout)
                .expectedDeliveryTime(expectedDeliveryTime)
                .build();

        delivery.editPreparationDetails(preparationDetails);

        for(ItemInput itemInput : input.getItems()) {
            delivery.addItem(itemInput.getName(), itemInput.getQuantity());
        }
    }

    private DeliveryOutput toDTO(Delivery delivery) {
        return DeliveryOutput.builder()
            .id(delivery.getId())
            .courierId(delivery.getCourierId())
            .status(delivery.getStatus())
            .placeAt(delivery.getPlaceAt())
            .assignedAt(delivery.getAssignedAt())
            .expectedDeliveryAt(delivery.getExpectedDeliveryAt())
            .fulfilledAt(delivery.getFulfilledAt())
            .distanceFee(delivery.getDistanceFee())
            .courierPayout(delivery.getCourierPayout())
            .totalCost(delivery.getTotalCost())
            .totalItems(delivery.getTotalItems())
            .sender(toContactPointInput(delivery.getSender()))
            .recipient(toContactPointInput(delivery.getRecipient()))
            .items(
                delivery.getItems().stream()
                    .map(item -> new ItemInput(item.getName(), item.getQuantity()))
                    .collect(Collectors.toList())
            )
            .build();
        }

        private ContactPointInput toContactPointInput(ContactPoint contactPoint) {
            return ContactPointInput.builder()
                    .zipCode(contactPoint.getZipCode())
                    .street(contactPoint.getStreet())
                    .number(contactPoint.getNumber())
                    .complement(contactPoint.getComplement())
                    .name(contactPoint.getName())
                    .phone(contactPoint.getPhone())
                    .build();
        }
}
