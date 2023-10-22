package com.test.spring.stocktrading.controller;

import com.test.spring.stocktrading.model.Stock;
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
    public Mono<Stock> getOneStock(@PathVariable String id) {
        return stockService.getOneStock(id);
    }

    @GetMapping
    public Flux<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @PostMapping
    public Mono<Stock> createStock(@RequestBody Stock stock) {
        return stockService.createStock(stock);
    }

}
