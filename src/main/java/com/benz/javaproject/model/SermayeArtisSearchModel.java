package com.benz.javaproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SermayeArtisSearchModel {
    private int yil;
    private double bedelliArtisMiktari;
    private double bedelsizArtisMiktari;
    private double sermayeArtisOrani;
    private double eskiSermaye;
}
