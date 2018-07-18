package com.onwelo.practice.bts.repository;

import com.onwelo.practice.bts.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT t FROM Transfer t WHERE t.status = :status")
    ArrayList<Transfer> findAllByState(@Param("status") String status);
}
