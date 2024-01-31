package com.benz.javaproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SermayeArtisSearchModel that = (SermayeArtisSearchModel) o;

        if (yil != that.yil) return false;
        if (!Objects.equals(bedelliArtisMiktari, that.bedelliArtisMiktari))
            return false;
        if (!Objects.equals(bedelsizArtisMiktari, that.bedelsizArtisMiktari))
            return false;
        if (!Objects.equals(sermayeArtisOrani, that.sermayeArtisOrani))
            return false;
        return Objects.equals(eskiSermaye, that.eskiSermaye);
    }

    @Override
    public int hashCode() {
        int result = yil;
        result = 31 * result + (bedelliArtisMiktari != null ? bedelliArtisMiktari.hashCode() : 0);
        result = 31 * result + (bedelsizArtisMiktari != null ? bedelsizArtisMiktari.hashCode() : 0);
        result = 31 * result + (sermayeArtisOrani != null ? sermayeArtisOrani.hashCode() : 0);
        result = 31 * result + (eskiSermaye != null ? eskiSermaye.hashCode() : 0);
        return result;
    }

}
