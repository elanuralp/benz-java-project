package com.benz.javaproject.entity;

import jakarta.annotation.Nullable;
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
public class HisseSenetleri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long senetId;

    @ManyToOne
    @JoinColumn(name = "tertip_id")
    private SermayeArtisi sermayeArtisi;

    @ManyToOne
    @JoinColumn(name = "hissedar_id",nullable = true)
    private Hissedarlar hissedar;

    private double nominalDeger;

    @Column(name = "kupur_no")
    private int kupurNo;

}
