package com.benz.javaproject.specification;

import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.model.SermayeArtisSearchModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

public class SermayeArtisiSpecification {

    public static Specification<SermayeArtisi> searchSermayeArtisiBySearchModel(SermayeArtisSearchModel searchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchModel.getYil() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("yil"), searchModel.getYil()));
            }

            if (searchModel.getBedelliArtisMiktari() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("bedelliArtisMiktari"), searchModel.getBedelliArtisMiktari()));
            }

            if (searchModel.getBedelsizArtisMiktari() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("bedelsizArtisMiktari"), searchModel.getBedelsizArtisMiktari()));
            }

            if (searchModel.getSermayeArtisOrani() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("sermayeArtisOrani"), searchModel.getSermayeArtisOrani()));
            }

            if (searchModel.getEskiSermaye() != 0) {
                predicates.add(criteriaBuilder.equal(root.get("eskiSermaye"), searchModel.getEskiSermaye()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
