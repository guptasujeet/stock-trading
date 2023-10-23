package org.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.common.model.Stock;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockPublishRequest {
    private String name;
    private BigDecimal price;
    private String currency;


    public static StockPublishRequest fromModel(Stock stock) {
        return StockPublishRequest.builder()
                .name(stock.getName())
                .price(stock.getPrice())
                .currency(stock.getCurrency())
                .build();
    }

    public Stock toStock() {
        return Stock.builder()
                .name(this.name)
                .price(this.price)
                .currency(this.currency)
                .build();
    }
}
