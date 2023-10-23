package org.example.market.service;

import org.example.common.dto.CurrencyRate;
import org.example.market.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class CurrencyRateService {

    private final CurrencyRepository currencyRepository;

    public CurrencyRateService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Flux<CurrencyRate> getCurrencyRates() {
        return currencyRepository.findAll()
                .map(CurrencyRate::fromModel);
    }
}
