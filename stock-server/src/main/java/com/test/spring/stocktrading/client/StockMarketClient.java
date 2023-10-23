package com.test.spring.stocktrading.client;

import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CurrencyRate;
import org.example.common.dto.StockPublishRequest;
import org.example.common.dto.StockPublishResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class StockMarketClient {

    private final WebClient webClient;

    public StockMarketClient(@Value("${clients.stockMarket.baseUrl}") String baseUrl) {
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Flux<CurrencyRate> getCurrencyRates() {
        return webClient.get()
                .uri("/currencyRates")
                .retrieve()
                .bodyToFlux(CurrencyRate.class)
                .doFirst(() -> log.info("Request to Currency Rates made"))
                .doOnNext(cr -> log.info("Currency Rate received -> {}", cr));
    }

    public Mono<StockPublishResponse> publishStockInfo(StockPublishRequest stockPublishRequest) {
        return webClient.post()
                .uri("/stocks/publish")
                .body(BodyInserters.fromValue(stockPublishRequest))
                .retrieve()
                .bodyToMono(StockPublishResponse.class)
                .doFirst(() -> log.info("Request to Stock publish made"))
                .doOnNext(st -> log.info("Stock received -> {}", st));
    }
}
