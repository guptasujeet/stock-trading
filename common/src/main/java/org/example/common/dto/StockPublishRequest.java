package org.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
        if (StringUtils.contains(this.name, "-")) {
            //simulate some exception
            throw new IllegalArgumentException("- Not allowed in the name : " + this.name);
        }
        return Stock.builder()
                .name(this.name)
                .price(this.price)
                .currency(this.currency)
                .build();
    }
}
