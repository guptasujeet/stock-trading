package com.test.spring.stocktrading.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.common.model.Stock;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StockRequest {

    @JsonProperty("stockName")
    private String name;
    private BigDecimal price;
    private String currency;

    public Stock toModel() {
        return Stock.builder()
                .name(this.name)
                .price(this.price)
                .currency(this.currency)
                .build();
    }

}
