package org.example.market.service;

import org.example.common.dto.StockPublishRequest;
import org.example.common.dto.StockPublishResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StockPublishService {
    public Mono<StockPublishResponse> publishStock(StockPublishRequest stockPublishRequest) {
        return null;
    }
}
