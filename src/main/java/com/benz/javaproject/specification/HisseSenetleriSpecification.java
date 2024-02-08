package com.benz.javaproject.specification;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.HisseSenetleri_;
import com.benz.javaproject.entity.SermayeArtisi;
import com.benz.javaproject.entity.SermayeArtisi_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;


    public class HisseSenetleriSpecification {

        public static Specification<HisseSenetleri> bySeriNo(int seriNo) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(HisseSenetleri_.seriNo), seriNo);
        }


    }

