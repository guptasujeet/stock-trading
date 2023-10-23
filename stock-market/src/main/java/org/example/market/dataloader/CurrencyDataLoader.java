package org.example.market.dataloader;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.example.common.model.Currency;
import org.example.market.repository.CurrencyRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Collection;

@Component
@Slf4j
public class CurrencyDataLoader implements ApplicationRunner {

    private final CurrencyRepository currencyRepository;

    public CurrencyDataLoader(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        currencyRepository.deleteAll().block();
        Currency usdToInr = Currency.builder().currency("INR").rate(new BigDecimal("83")).build();
        Currency usdToUsd = Currency.builder().currency("USD").rate(new BigDecimal("1")).build();
        Currency usdToEuro = Currency.builder().currency("EUR").rate(new BigDecimal("0.94")).build();
        Collection<Currency> currencies = Lists.newArrayList(usdToInr, usdToEuro, usdToUsd);
        Flux<Currency> currencyFlux = currencyRepository.saveAll(currencies);
        currencyFlux.blockLast();
        log.info("Done loading data");
    }
}
