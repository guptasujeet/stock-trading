package org.example.market.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.CurrencyRate;
import org.example.common.dto.StockPublishRequest;
import org.example.common.dto.StockPublishResponse;
import org.example.market.service.CurrencyRateService;
import org.example.market.service.StockPublishService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class StockMarketController {

    private CurrencyRateService currencyRateService;
    private StockPublishService stockPublishService;

    public StockMarketController(CurrencyRateService currencyRateService, StockPublishService stockPublishService) {
        this.currencyRateService = currencyRateService;
        this.stockPublishService = stockPublishService;
    }

    @GetMapping("/currencyRates")
    public Flux<CurrencyRate> getCurrencyRate(@RequestHeader(value = "X-Trace-Id", required = false) String traceId) {
        log.info("Currency Rate called with traceId -> {}", traceId);
        return currencyRateService.getCurrencyRates();
    }

    @PostMapping("/stocks/publish")
    public Mono<StockPublishResponse> publishStock(@RequestBody StockPublishRequest stockPublishRequest,
                                                   @RequestHeader(value = "X-Trace-Id", required = false) String traceId) {
        log.info("Publish stock called with traceId -> {}", traceId);
        return stockPublishService.publishStock(stockPublishRequest);
    }

}
