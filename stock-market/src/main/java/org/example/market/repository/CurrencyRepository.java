package org.example.market.repository;

import org.example.market.model.Currency;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CurrencyRepository extends ReactiveMongoRepository<Currency, String> {
}
