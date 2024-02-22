package com.benz.javaproject.entity;

import com.benz.javaproject.enums.KuponTuru;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Kuponlar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonIgnore
    private int kuponId;

    @Enumerated(EnumType.STRING)
    private KuponTuru kuponTuru;


    private Integer kuponYili;
    private Integer kupurNo;
    private int kuponNumarasi;
    private boolean kullanildiMi;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "senet_id")
    @JsonIgnore
    private HisseSenetleri senet;

}