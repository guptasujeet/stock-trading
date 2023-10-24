package com.test.spring.stocktrading.service;

import com.test.spring.stocktrading.client.StockMarketClient;
import com.test.spring.stocktrading.dto.StockRequest;
import com.test.spring.stocktrading.repository.StockRepository;
import org.example.common.dto.StockPublishRequest;
import org.example.common.dto.StockPublishResponse;
import org.example.common.model.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class StockServiceTest {


    private static final String TEST_ID = "6536a60221d0c65d450924d0";
    private static final String TEST_NAME = "IBM";
    private static final String TEST_CURRENCY = "USD";
    private static final BigDecimal TEST_PRICE = BigDecimal.TEN;

    @Mock
    private StockRepository stockRepository;
    @Mock
    private StockMarketClient stockMarketClient;

    @InjectMocks
    private StockService stockService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCreateStock() {
        //given
        StockRequest stockRequest = StockRequest.builder().name(TEST_NAME).price(TEST_PRICE).currency(TEST_CURRENCY).build();
        Stock stock = stockRequest.toModel();
        stock.setId(TEST_ID);

        StockPublishResponse stockPublishResponse = StockPublishResponse.fromModel(stock);
        //when
        Mockito.when(stockRepository.save(any(Stock.class))).thenReturn(Mono.just(stock));
        Mockito.when(stockMarketClient.publishStockInfo(any(StockPublishRequest.class))).thenReturn(Mono.just(stockPublishResponse));
        StepVerifier.create(stockService.createStock(stockRequest)) //notice StepVerifier here
                //then
                .assertNext(stockResponse -> {
                    assertNotNull(stockResponse);
                    assertEquals(TEST_ID, stockResponse.getId());
                    assertEquals(TEST_CURRENCY, stockResponse.getCurrency());
                    assertEquals(TEST_PRICE, stockResponse.getPrice());
                    assertEquals(TEST_NAME, stockResponse.getName());
                })
                .verifyComplete();

    }

}
