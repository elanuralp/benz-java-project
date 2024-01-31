package com.benz.javaproject.entity;

import com.benz.javaproject.enums.KuponTuru;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HisseSenetleri {
    @Id  //seri no 1 den başlicak ardışık ilerlicek
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seri_no_generator")
    @SequenceGenerator(name = "seri_no_generator", sequenceName = "seri_no_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "seri_no", nullable = false, unique = true)
    private int seriNo;

    @ManyToOne
    @JoinColumn(name = "tertip_no")
    private SermayeArtisi sermayeArtisi;



    @ManyToOne
    @JoinColumn(name = "hissedar_id",nullable = true)
    private Hissedarlar hissedar;

    @Column(name = "nominal_deger")
    private double nominalDeger;
}
