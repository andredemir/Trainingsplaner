package com.haw.srs.customerservice.repositories;

import com.haw.srs.customerservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for all bills
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByBillId(Long billId);
}
