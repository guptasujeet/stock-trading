package org.example.market.service;

import org.example.common.dto.StockPublishRequest;
import org.example.common.dto.StockPublishResponse;
import org.example.market.repository.StockRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StockPublishService {

    private final StockRepository stockRepository;

    public StockPublishService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Mono<StockPublishResponse> publishStock(StockPublishRequest stockPublishRequest) {
        return Mono.just(stockPublishRequest)
                .map(StockPublishRequest::toStock)
                .flatMap(stockRepository::save)
                .map(StockPublishResponse::fromModel);
    }
}
