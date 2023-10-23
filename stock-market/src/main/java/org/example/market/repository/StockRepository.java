package org.example.market.repository;

import org.example.common.model.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends ReactiveMongoRepository<Stock, String> {
}
