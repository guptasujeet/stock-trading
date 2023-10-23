package com.test.spring.stocktrading.service;

import com.test.spring.stocktrading.dto.StockRequest;
import com.test.spring.stocktrading.dto.StockResponse;
import com.test.spring.stocktrading.exception.StockCreationException;
import com.test.spring.stocktrading.exception.StockNotFoundException;
import com.test.spring.stocktrading.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }


    public Mono<StockResponse> getOneStock(String id) {
        return stockRepository.findById(id)
                .map(StockResponse::fromModel)
                .switchIfEmpty(Mono.error(new StockNotFoundException("Stock not found with id: " + id)));
    }

    public Flux<StockResponse> getAllStocks() {
        return stockRepository.findAll()
                .map(StockResponse::fromModel);
    }

    public Mono<StockResponse> createStock(StockRequest stockRequest) {
        return Mono.just(stockRequest)
                .map(StockRequest::toModel)
                .flatMap(stockRepository::save)
                .map(StockResponse::fromModel)
                //.onErrorReturn(StockResponse.builder().build());//fallback
                /*.onErrorResume(ex -> {
                    log.warn("Could not create stock: {} , Exception :", stockRequest, ex);
                    return Mono.just(StockResponse.builder().build());
                });*/
                .onErrorMap(ex -> new StockCreationException(ex.getMessage()));
    }
}
