package org.example.market.service;

import org.example.common.dto.CurrencyRate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CurrencyRateService {
    public Flux<CurrencyRate> getCurrencyRates() {
        return null;
    }
}
