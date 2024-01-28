package com.benz.javaproject.repository;

import com.benz.javaproject.entity.Hissedarlar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HissedarlarRepository extends JpaRepository<Hissedarlar,Long> {
    //field search
}
