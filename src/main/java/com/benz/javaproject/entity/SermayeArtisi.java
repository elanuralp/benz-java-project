package com.benz.javaproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SermayeArtisi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tertipNo;

    private int yil;
    private BigDecimal bedelliArtisMiktari;
    private BigDecimal bedelsizArtisMiktari;
    private BigDecimal sermayeArtisOrani;
    private BigDecimal eskiSermaye;

}
