package com.benz.javaproject.specification;

import com.benz.javaproject.entity.*;
import com.benz.javaproject.enums.KuponTuru;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class KuponlarSpecification {

    public static Specification<Kuponlar> kuponlarBySenetId(Long senetId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Kuponlar_.senet).get("senetId"), senetId);
    }

    public static Specification<Kuponlar> searchPayAlmaKuponuByTertipNo(Long tertipNo){
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if (tertipNo!=null){
                predicates.add(criteriaBuilder.equal(root.join(Kuponlar_.senet).join(HisseSenetleri_.sermayeArtisi).get(SermayeArtisi_.tertipNo),tertipNo));
            }
            predicates.add(criteriaBuilder.isNotNull(root.get(Kuponlar_.KUPUR_NO)));
            query.orderBy(criteriaBuilder.asc(root.get(Kuponlar_.KUPUR_NO)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }


    public static Specification<Kuponlar> searchEnKucukKupurNoluPayAlmaKuponuListBySeriNo(Long seriNo) {
        return (Root<Kuponlar> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (seriNo != null) {
                Join<Kuponlar, HisseSenetleri> senetJoin = root.join(Kuponlar_.senet, JoinType.INNER);
                predicates.add(criteriaBuilder.equal(senetJoin.get(HisseSenetleri_.SERI_NO), seriNo));
            }

            predicates.add(criteriaBuilder.equal(root.get(Kuponlar_.kuponTuru), KuponTuru.PAY_ALMA));
            predicates.add(criteriaBuilder.isNotNull(root.get(Kuponlar_.KUPUR_NO)));

            // Sıralamayı küpür numarasına göre artan şekilde yap
            query.orderBy(criteriaBuilder.asc(root.get(Kuponlar_.KUPUR_NO)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static Specification<HisseSenetleri> searchBySeriNo(Long seriNo) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicateSeriNo = criteriaBuilder.equal(root.get("seriNo"), seriNo);
            return criteriaBuilder.and(predicateSeriNo);
        };
    }

    public static Specification<Kuponlar> searchKullanilmamisPayAlmaKuponlarBySenet(HisseSenetleri senet) {
        return (Root<Kuponlar> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Belirtilen senete ait kuponları al
            Join<Kuponlar, HisseSenetleri> senetJoin = root.join(Kuponlar_.senet, JoinType.INNER);
            predicates.add(criteriaBuilder.equal(senetJoin, senet));

            // Pay alma kuponlarını seç
            predicates.add(criteriaBuilder.equal(root.get(Kuponlar_.kuponTuru), KuponTuru.PAY_ALMA));

            // Kullanılmamış kuponları seç
            predicates.add(criteriaBuilder.equal(root.get(Kuponlar_.kullanildiMi), false));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
