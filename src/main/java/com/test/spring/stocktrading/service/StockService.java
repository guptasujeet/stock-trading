package com.test.spring.stocktrading.service;

import com.test.spring.stocktrading.model.Stock;
import com.test.spring.stocktrading.repository.StockRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public Mono<Stock> getOneStock(String id) {
        return stockRepository.findById(id);
    }

    public Flux<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Mono<Stock> createStock(Stock stock) {
        return stockRepository.save(stock);
    }
}
