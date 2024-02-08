package com.benz.javaproject.repository;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.KarPayiDagitimi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KarPayıDagıtımıRepository extends JpaRepository<KarPayiDagitimi,Long>, JpaSpecificationExecutor<KarPayiDagitimi> {


}
