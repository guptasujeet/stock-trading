package com.test.spring.stocktrading.controller;

import com.test.spring.stocktrading.dto.StockRequest;
import com.test.spring.stocktrading.dto.StockResponse;
import com.test.spring.stocktrading.service.StockService;
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


    @GetMapping("/{id}")
    public Mono<StockResponse> getOneStock(@PathVariable String id) {
        return stockService.getOneStock(id);
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
