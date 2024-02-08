package com.benz.javaproject.model.SermayeArtisi;

import com.benz.javaproject.entity.Hissedarlar;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SermayeArtısıAddModel {

    private BigDecimal bedelliArtisMiktari;
    Integer yıl;
    private BigDecimal bedelsizArtisMiktari;
    private BigDecimal sermayeArtisOrani;
    private BigDecimal eskiSermaye;




}

