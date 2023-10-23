package org.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.common.model.Currency;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRate {
    private BigDecimal rate;
    private String currency;

    public static CurrencyRate fromModel(Currency currency) {
        return CurrencyRate.builder()
                .rate(currency.getRate())
                .currency(currency.getCurrency())
                .build();
    }

}
