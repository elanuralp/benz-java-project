package com.benz.javaproject.entity;

import com.benz.javaproject.enums.IslemTipi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class IslemKayitlari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long kayitId;

    @Enumerated(EnumType.STRING)
    private IslemTipi islemTipi;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dagitimId")
    private KarPayiDagitimi karPayiDagitimi;
    private LocalDateTime islemZamani;
    private BigDecimal karPayiTutari;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "senetId")
    private HisseSenetleri hisseSenetleri;
    private Integer seriNo;



    @PrePersist
    public void prePersist() {
        this.islemZamani = LocalDateTime.now();
    }

}
