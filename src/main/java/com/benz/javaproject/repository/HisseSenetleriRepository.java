package com.benz.javaproject.repository;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Hissedarlar;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HisseSenetleriRepository extends JpaRepository<HisseSenetleri,Long>, JpaSpecificationExecutor<HisseSenetleri> {

    @Query("SELECT MAX(hs.seriNo) FROM HisseSenetleri hs WHERE hs.sermayeArtisi.tertipNo = :tertipNo")
    Integer findMaxSeriNoByTertipNo(@Param("tertipNo") Long tertipNo);



}
