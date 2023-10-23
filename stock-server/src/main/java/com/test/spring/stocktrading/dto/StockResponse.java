package com.test.spring.stocktrading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.common.dto.StockPublishResponse;
import org.example.common.model.Stock;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StockResponse {

    private String id;

    @JsonProperty("stockName")
    private String name;
    private BigDecimal price;
    private String currency;

    public static StockResponse fromModel(Stock stock) {
        return StockResponse.builder()
                .id(stock.getId())
                .name(stock.getName())
                .price(stock.getPrice())
                .currency(stock.getCurrency())
                .build();
    }

    public static StockResponse fromStockPublishResponse(StockPublishResponse response) {
        return StockResponse.builder()
                .id(response.getId())
                .name(response.getName())
                .price(response.getPrice())
                .currency(response.getCurrency())
                .build();
    }

}
