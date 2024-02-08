package com.benz.javaproject.specification;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.KarPayiDagitimi;
import com.benz.javaproject.entity.Kuponlar;
import com.benz.javaproject.enums.KuponTuru;
import org.springframework.data.jpa.domain.Specification;

public class KarPayiDagitimiSpecification {



    public static Specification<Kuponlar> getUygunKarPayiKuponuSpec(HisseSenetleri senet, int dagitimYili) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("senet"), senet),
                    criteriaBuilder.equal(root.get("kuponTuru"), KuponTuru.KAR_PAYI),
                    criteriaBuilder.equal(root.get("kuponYili"), dagitimYili)
            );
        };
    }
}
