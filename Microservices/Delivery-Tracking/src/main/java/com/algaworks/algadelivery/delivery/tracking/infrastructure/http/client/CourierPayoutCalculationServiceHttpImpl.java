package com.algaworks.algadelivery.delivery.tracking.infrastructure.http.client;

import com.algaworks.algadelivery.delivery.tracking.domain.service.CourierPayoutCalculationService;
import com.algaworks.algadelivery.delivery.tracking.infrastructure.http.exception.BadGatewayException;
import com.algaworks.algadelivery.delivery.tracking.infrastructure.http.exception.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CourierPayoutCalculationServiceHttpImpl implements CourierPayoutCalculationService {

    private final CourierAPIClient courierAPIClient;

    @Override
    public BigDecimal calculatePayout(Double distanceInKm) {
        try {
            var courierPayoutResultModel = courierAPIClient.payoutCalculation(
                    new CourierPayoutCalculationInput(distanceInKm));
            return courierPayoutResultModel.getPayoutFee();
        } catch(ResourceAccessException e) {
            throw new GatewayTimeoutException(e);
        } catch (HttpServerErrorException | IllegalArgumentException e) {
            throw new BadGatewayException(e);
        }
    }
}
