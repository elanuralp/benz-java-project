package com.benz.javaproject.entity;

import com.benz.javaproject.enums.IslemTipi;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class IslemKayitları {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kayitId;

    @Enumerated(EnumType.STRING)
    private IslemTipi islemTipi;

    @ManyToOne
    @JoinColumn(name = "senet_id")
    private HisseSenetleri senet;

    @ManyToOne
    @JoinColumn(name = "dagitim_id")
    private KarPayıDağıtımı karPayıDağıtımı;

    @ManyToOne
    @JoinColumn(name = "kuponId")
    private Kuponlar kupon;



    private LocalDateTime islemZamani;

}
