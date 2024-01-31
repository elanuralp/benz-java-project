package com.benz.javaproject.repository;

import com.benz.javaproject.entity.HisseSenetleri;
import com.benz.javaproject.entity.Hissedarlar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HisseSenetleriRepository extends JpaRepository<HisseSenetleri,Long> {

}
