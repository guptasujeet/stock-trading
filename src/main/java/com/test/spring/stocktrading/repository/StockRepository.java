package com.test.spring.stocktrading.repository;

import com.test.spring.stocktrading.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, String> {
}