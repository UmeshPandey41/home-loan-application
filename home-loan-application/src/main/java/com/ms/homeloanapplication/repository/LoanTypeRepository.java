package com.ms.homeloanapplication.repository;

import com.ms.homeloanapplication.model.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
    boolean existsByName(String name);
    LoanType findByName(String name);
}
