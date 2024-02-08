package com.benz.javaproject.specification;

import com.benz.javaproject.entity.Hissedarlar;
import com.benz.javaproject.entity.Hissedarlar_;
import com.benz.javaproject.model.hissedar.HissedarSearchModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class HissedarlarSpecification {

    public static Specification<Hissedarlar> searchHissedarlarBySearchModel(HissedarSearchModel hissedarlarSearchModel) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(hissedarlarSearchModel.getUnvan())) {
                String unvanLower = hissedarlarSearchModel.getUnvan().toLowerCase().trim();
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(root.get(Hissedarlar_.UNVAN)),
                        unvanLower
                ));
            }

            if (StringUtils.hasText(hissedarlarSearchModel.getAdres())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Hissedarlar_.ADRES)),
                        "%" + hissedarlarSearchModel.getAdres().toLowerCase().trim() + "%"
                ));
            }

            if (StringUtils.hasText(hissedarlarSearchModel.getTelefon())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Hissedarlar_.TELEFON)),
                        "%" + hissedarlarSearchModel.getTelefon().toLowerCase().trim() + "%"
                ));
            }
            if (hissedarlarSearchModel.getYatirimciTipi() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get(Hissedarlar_.YATIRIMCI_TIPI),
                        hissedarlarSearchModel.getYatirimciTipi()
                ));
            }

            if (StringUtils.hasText(hissedarlarSearchModel.getSicilNumarasi())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(Hissedarlar_.SICIL_NUMARASI)),
                        "%" + hissedarlarSearchModel.getSicilNumarasi().toLowerCase().trim() + "%"
                ));
            }
            // Diğer kriterleri buraya ekleyebilirsiniz.
            query.orderBy(criteriaBuilder.asc(root.get(Hissedarlar_.UNVAN)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
