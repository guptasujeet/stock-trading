package com.test.spring.stocktrading.client;

import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CurrencyRate;
import org.example.common.dto.StockPublishRequest;
import org.example.common.dto.StockPublishResponse;
import org.example.common.exception.StockCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class StockMarketClient {

    private final WebClient webClient;

    public StockMarketClient(@Value("${clients.stockMarket.baseUrl}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(ExchangeFilterFunction.ofRequestProcessor(
                        request -> Mono.just(ClientRequest.from(request)
                                .header("X-Trace-Id", UUID.randomUUID().toString())
                                .build())
                ))
                .build();
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
                .onStatus(HttpStatusCode::isError, (res) -> {
                    return res.bodyToMono(ProblemDetail.class)
                            .switchIfEmpty(Mono.error(new StockCreationException("Error during stock creation")))
                            .flatMap(problemDetail -> Mono.error(new StockCreationException(problemDetail.getDetail())));

                })
                .bodyToMono(StockPublishResponse.class)
                .doFirst(() -> log.info("Request to Stock publish made"))
                .doOnNext(st -> log.info("Stock received -> {}", st));
    }
}
