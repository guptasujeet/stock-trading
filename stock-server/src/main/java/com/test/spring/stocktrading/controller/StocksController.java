package com.test.spring.stocktrading.controller;

import com.test.spring.stocktrading.dto.StockRequest;
import com.test.spring.stocktrading.dto.StockResponse;
import com.test.spring.stocktrading.service.StockService;
import io.micrometer.common.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/stocks")
public class StocksController {

    private final StockService stockService;

    public StocksController(StockService stockService) {
        this.stockService = stockService;
    }


    @GetMapping("/{id}/{currency}}")
    public Mono<StockResponse> getOneStock(@PathVariable String id, @PathVariable(required = false) String currency) {
        currency = StringUtils.isBlank(currency) ? "INR" : currency;
        return stockService.getOneStock(id, currency);
    }

    @GetMapping
    public Flux<StockResponse> getAllStocks() {
        return stockService.getAllStocks();
    }

    @PostMapping
    public Mono<StockResponse> createStock(@RequestBody StockRequest stock) {
        return stockService.createStock(stock);
    }

}
