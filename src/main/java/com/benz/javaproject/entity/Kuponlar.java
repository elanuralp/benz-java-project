package com.benz.javaproject.entity;

import com.benz.javaproject.enums.KuponTuru;
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
    private Long kuponId;

    @ManyToOne
    @JoinColumn(name = "senet_id")
    private HisseSenetleri senet;

    private int kuponNumarasi;

    @Enumerated(EnumType.STRING)
    private KuponTuru kuponTuru;

    private int kuponYili;
    private boolean kullanildiMi;
}
