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
    @Id  //kupon no 1 den başlicak ardışık ilerlicek
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kupon_no_generator")
    @SequenceGenerator(name = "kupon_no_generator", sequenceName = "kupon_no_seq", initialValue = 1, allocationSize = 1)
    @Column(name = "kuponNumarasi", nullable = false, unique = true)
    private Long kuponNumarasi;

    @Enumerated(EnumType.STRING)
    private KuponTuru kuponTuru;

    private int kuponYili;
    private Integer kupurNo;
    private boolean kullanildiMi;

    @ManyToOne
    @JoinColumn(name = "senet_id")
    private HisseSenetleri senet;


    @PrePersist
    public void prePersist() {
        // PAY ALMA türündeki kuponlarda kupurNo değeri atanır
        if (KuponTuru.PAY_ALMA.equals(this.kuponTuru)) {
            this.kupurNo = kupurNo; // Küpür numarasını başka bir mekanizma ile belirleyin (örneğin, hisse senedi üzerinden alınabilir)
        }
    }

}
