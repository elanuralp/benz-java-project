package com.benz.javaproject.specification;

import com.benz.javaproject.entity.*;
import com.benz.javaproject.enums.KuponTuru;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


    public class HisseSenetleriSpecification {

        public static Specification<HisseSenetleri> bySeriNo(int seriNo) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(HisseSenetleri_.seriNo), seriNo);
        }

        public static Specification<HisseSenetleri> searchByTertipNo(Long tertipNo) {
            return (root, query, criteriaBuilder) -> {
                Join<HisseSenetleri, SermayeArtisi> sermayeArtisiJoin = root.join("sermayeArtisi");
                return criteriaBuilder.equal(sermayeArtisiJoin.get("tertipNo"), tertipNo);
            };
        }

        public static Specification<HisseSenetleri> searchByHissedarId(Long hissedarId) {
            return new Specification<HisseSenetleri>() {
                @Override
                public Predicate toPredicate(Root<HisseSenetleri> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("hissedar").get("id"), hissedarId);
                }
            };
        }
        public static Specification<HisseSenetleri> hisseSenediNominalDegerleri(Long senetId) {
            return new Specification<HisseSenetleri>() {
                @Override
                public Predicate toPredicate(Root<HisseSenetleri> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.equal(root.get("senetId"), senetId);
                }
            };
        }





    }

