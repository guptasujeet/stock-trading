package com.test.spring.stocktrading.integration;

import com.test.spring.stocktrading.client.StockMarketClient;
import com.test.spring.stocktrading.controller.StocksController;
import com.test.spring.stocktrading.dto.StockRequest;
import com.test.spring.stocktrading.dto.StockResponse;
import com.test.spring.stocktrading.repository.StockRepository;
import com.test.spring.stocktrading.service.StockService;
import org.example.common.dto.CurrencyRate;
import org.example.common.model.Stock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@WebFluxTest(controllers = StocksController.class)
@ActiveProfiles("test")
@Import(StockService.class)
public class StockControllerIntegrationTest {
    private static final String TEST_ID = "6536a60221d0c65d450924d0";
    private static final String TEST_NAME = "IBM";
    private static final String TEST_CURRENCY = "USD";
    private static final BigDecimal TEST_PRICE = BigDecimal.TEN;

    @MockBean
    private StockRepository stockRepository;

    @MockBean
    private StockMarketClient stockMarketClient;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldGetOneStock() {
        //given
        StockRequest stockRequest = buildTestStockRequest();
        Stock stock = buildStockFromStockRequest(stockRequest);
        CurrencyRate currencyRate = buildCurrencyRate();

        Mockito.when(stockRepository.findById(eq(TEST_ID))).thenReturn(Mono.just(stock));
        Mockito.when(stockMarketClient.getCurrencyRates()).thenReturn(Flux.just(currencyRate));

        String expectedCurrency = "INR";

        //when
        StockResponse stockResponse = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks/{id}").queryParam("currency", expectedCurrency).build(TEST_ID))
                .exchange()
                .expectStatus().isOk()
                .expectBody(StockResponse.class)
                .returnResult()
                .getResponseBody();


        //then
        assertNotNull(stockResponse);
        assertEquals(TEST_ID, stockResponse.getId());
        assertEquals(expectedCurrency, stockResponse.getCurrency());
        assertEquals(new BigDecimal("834.0"), stockResponse.getPrice());
        assertEquals(TEST_NAME, stockResponse.getName());
    }

    @Test
    public void shouldReturnNotFoundWhenGetOneStock_NotPresent() {
        //given
        Mockito.when(stockRepository.findById(eq(TEST_ID))).thenReturn(Mono.empty());

        String expectedCurrency = "INR";

        //when
        ProblemDetail problemDetail = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stocks/{id}").queryParam("currency", expectedCurrency).build(TEST_ID))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult()
                .getResponseBody();

        //then
        assertNotNull(problemDetail);
        assertNotNull(problemDetail.getDetail());
        assertTrue(problemDetail.getDetail().contains("Stock not found"));
    }


    private StockRequest buildTestStockRequest() {
        return StockRequest.builder().name(TEST_NAME).price(TEST_PRICE).currency(TEST_CURRENCY).build();
    }

    private Stock buildStockFromStockRequest(StockRequest stockRequest) {
        Stock stock = stockRequest.toModel();
        stock.setId(TEST_ID);
        return stock;
    }

    private CurrencyRate buildCurrencyRate() {
        return CurrencyRate.builder()
                .rate(new BigDecimal("83.4"))
                .currency("INR")
                .build();
    }
}
