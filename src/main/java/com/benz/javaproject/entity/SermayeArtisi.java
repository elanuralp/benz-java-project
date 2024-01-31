package com.benz.javaproject.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private double bedelliArtisMiktari;
    private double bedelsizArtisMiktari;
    private double sermayeArtisOrani;
    private double eskiSermaye;

}
