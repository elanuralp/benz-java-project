package com.benz.javaproject.entity;

import ch.qos.logback.core.model.processor.NOPModelHandler;
import com.benz.javaproject.enums.KuponTuru;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class HisseSenetleri {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long senetId;

    private int seriNo;

    @ManyToOne
    @JoinColumn(name = "tertip_no")
    private SermayeArtisi sermayeArtisi;



    @ManyToOne
    @JoinColumn(name = "hissedar_id",nullable = true)
    private Hissedarlar hissedar;

    @Column(name = "nominal_deger")
    private BigDecimal nominalDeger;



    @OneToMany(mappedBy = "senet", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Kuponlar> kuponlarList = new ArrayList<>();

    @OneToMany(mappedBy = "dagitimId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<KarPayiDagitimi> karPayiDagitimiList = new ArrayList<>();




}