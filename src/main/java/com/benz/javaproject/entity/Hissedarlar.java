package com.benz.javaproject.entity;

import com.benz.javaproject.enums.YatirimciTipi;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hissedarlar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hissedarId;
    private String unvan;
    private String adres;
    private String telefon;

    @Enumerated(EnumType.STRING)
    private YatirimciTipi yatirimciTipi;

    @Column(unique = true)
    private String sicilNumarasi;

    @OneToMany(mappedBy = "hissedar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HisseSenetleri> hisseSenetleriList;
}
