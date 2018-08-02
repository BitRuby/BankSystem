package com.onwelo.practice.bts.repository;

import com.onwelo.practice.bts.entity.Transfer;
import com.onwelo.practice.bts.utils.TransferStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TransferRepository extends PagingAndSortingRepository<Transfer, Long> {
    ArrayList<Transfer> findAll();

    ArrayList<Transfer> findAllByStatus(TransferStatus status);

    Page<Transfer> findAllByAccountId_Id(Long account_id, Pageable pageable);
}
