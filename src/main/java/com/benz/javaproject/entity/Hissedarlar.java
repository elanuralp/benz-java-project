package com.benz.javaproject.entity;

import com.benz.javaproject.enums.YatirimciTipi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hissedarlar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore

    private Long hissedarId;
    private String unvan;

    private String adres;

    private String telefon;

    @Enumerated(EnumType.STRING)
    private YatirimciTipi yatirimciTipi;

    @Column(unique = true, length = 8) //0 ile başlamicak yapıcaksın
    private String sicilNumarasi;

    @JsonIgnore
    @OneToMany(mappedBy = "hissedar", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<HisseSenetleri> senetlerList = new ArrayList<>();

}