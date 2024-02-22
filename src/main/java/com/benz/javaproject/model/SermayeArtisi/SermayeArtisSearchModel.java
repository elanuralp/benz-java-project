package com.benz.javaproject.model.SermayeArtisi;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SermayeArtisSearchModel {
    private int yil;
    private BigDecimal bedelliArtisMiktari;
    private BigDecimal bedelsizArtisMiktari;
    private BigDecimal sermayeArtisOrani;
    private BigDecimal eskiSermaye;


}
