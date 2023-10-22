package com.test.spring.stocktrading.service;

import com.test.spring.stocktrading.model.Stock;
import com.test.spring.stocktrading.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public Optional<Stock> getOneStock(String id) {
        return stockRepository.findById(id);
    }

    public Iterable<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }
}
