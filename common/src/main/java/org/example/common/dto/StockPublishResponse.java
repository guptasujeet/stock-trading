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
public class StockPublishResponse {
    private String id;
    private String name;
    private BigDecimal price;
    private String currency;

    public static StockPublishResponse fromModel(Stock stock) {
        return StockPublishResponse.builder()
                .name(stock.getName())
                .id(stock.getId())
                .price(stock.getPrice())
                .currency(stock.getCurrency())
                .build();
    }
}
