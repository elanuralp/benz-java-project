package com.benz.javaproject.repository;

import com.benz.javaproject.entity.Hissedarlar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HissedarlarRepository extends JpaRepository<Hissedarlar,Long>,JpaSpecificationExecutor<Hissedarlar>{
    Optional<Hissedarlar> findBySicilNumarasi(String sicilNumarasi);





}