package com.benz.javaproject.repository;

import com.benz.javaproject.entity.SermayeArtisi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SermayeArtisiRepository extends JpaRepository<SermayeArtisi,Long>, JpaSpecificationExecutor<SermayeArtisi> {
}
