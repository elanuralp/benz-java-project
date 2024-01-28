package com.benz.javaproject.entity;

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
public class KarPayıDağıtımı {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dagitimId;

    @ManyToOne
    @JoinColumn(name = "tertip_id")
    private SermayeArtisi sermayeArtisi;

    private double karPayiOrani;
    private int dagitimYili;
    private double karPayiTutari;
    private int seriNo;


}
