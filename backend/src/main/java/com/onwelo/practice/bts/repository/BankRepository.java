package com.onwelo.practice.bts.repository;

import com.onwelo.practice.bts.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {

    Optional<Bank> findBySortCode(String sortCode);
}
