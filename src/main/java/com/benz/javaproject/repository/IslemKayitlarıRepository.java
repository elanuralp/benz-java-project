package com.benz.javaproject.repository;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.IslemKayitlari;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IslemKayitlarıRepository extends JpaRepository<IslemKayitlari,Long>, JpaSpecificationExecutor<IslemKayitlari> {
}
