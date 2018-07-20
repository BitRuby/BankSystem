package com.onwelo.practice.bts.repository;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    ArrayList<Transfer> findAllByStatus(TransferStatus status);

    void deleteAll();
}
