package com.benz.javaproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SermayeArtisi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long tertipNo;

    private Integer yil;
    private BigDecimal bedelliArtisMiktari;
    private BigDecimal bedelsizArtisMiktari;
    private BigDecimal sermayeArtisOrani;
    private BigDecimal eskiSermaye;

    @JsonIgnore
    @OneToMany(mappedBy = "sermayeArtisi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HisseSenetleri> hisseSenetleriList = new ArrayList<>();

}
