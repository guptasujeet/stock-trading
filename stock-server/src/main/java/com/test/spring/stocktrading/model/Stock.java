package com.test.spring.stocktrading.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document
public class Stock {

    private String id;
    private String name;
    @NonNull
    private BigDecimal price;
    private String currency;

}
