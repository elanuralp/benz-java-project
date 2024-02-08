package com.benz.javaproject.entity;

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
public class KarPayiDagitimi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long dagitimId;

    @ManyToOne
    @JoinColumn(name = "tertip_id")
    private SermayeArtisi sermayeArtisi;

    private int dagitimYili;
    private int seriNo; //o yıl içinde kaçıncı kar payı olduğu belli olsun
}
