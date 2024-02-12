package com.benz.javaproject.repository;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.IslemKayitlari;
import com.benz.javaproject.entity.Kuponlar;
import com.benz.javaproject.enums.KuponTuru;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface KuponlarRepository extends JpaRepository<Kuponlar,Long>, JpaSpecificationExecutor<Kuponlar> {

    @Query("SELECT MAX(k.kuponNumarasi) FROM Kuponlar k WHERE k.senet.id = :senetId")
    Integer findMaxKuponNumarasiBySenetId(@Param("senetId") Long senetId);




}
