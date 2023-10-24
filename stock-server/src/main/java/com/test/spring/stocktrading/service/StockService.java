package com.test.spring.stocktrading.service;

import com.test.spring.stocktrading.client.StockMarketClient;
import com.test.spring.stocktrading.dto.StockRequest;
import com.test.spring.stocktrading.dto.StockResponse;
import com.test.spring.stocktrading.exception.StockNotFoundException;
import com.test.spring.stocktrading.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.example.common.dto.StockPublishRequest;
import org.example.common.exception.StockCreationException;
import org.example.common.model.Stock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class StockService {

    private final StockRepository stockRepository;
    private final StockMarketClient stockMarketClient;

    public StockService(StockRepository stockRepository, StockMarketClient stockMarketClient) {
        this.stockRepository = stockRepository;
        this.stockMarketClient = stockMarketClient;
    }


    public Mono<StockResponse> getOneStock(String id, String currency) {
        return stockRepository.findById(id)
                .flatMap(stock -> getStockRateByCurrency(stock, currency))
                .switchIfEmpty(Mono.error(new StockNotFoundException("Stock not found with id: " + id)));
    }

    private Mono<StockResponse> getStockRateByCurrency(Stock stock, String currency) {
        return stockMarketClient.getCurrencyRates()
                .filter(currencyRate -> currencyRate.getCurrency().equals(currency))
                .singleOrEmpty()
                .map(currencyRate -> StockResponse.builder()
                        .id(stock.getId())
                        .name(stock.getName())
                        .currency(currency)
                        .price(stock.getPrice().multiply(currencyRate.getRate()))
                        .build());
    }

    public Flux<StockResponse> getAllStocks() {
        return stockRepository.findAll()
                .map(StockResponse::fromModel);
    }

    @Transactional
    public Mono<StockResponse> createStock(StockRequest stockRequest) {
        return Mono.just(stockRequest)
                .map(StockRequest::toModel)
                .flatMap(stockRepository::save)
                .map(StockPublishRequest::fromModel)
                .flatMap(stockMarketClient::publishStockInfo)
                .switchIfEmpty(Mono.error(new StockCreationException("Unable to publish stock")))
                .map(StockResponse::fromStockPublishResponse)
                .onErrorMap(ex -> new StockCreationException(ex.getMessage()));
    }
}
