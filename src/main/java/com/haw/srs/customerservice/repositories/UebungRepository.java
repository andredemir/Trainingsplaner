package com.haw.srs.customerservice.repositories;

import com.haw.srs.customerservice.entities.Bill;
import com.haw.srs.customerservice.entities.Uebung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for all excersises
 */
@Repository
public interface UebungRepository extends JpaRepository<Uebung, Long> {
    Optional<Uebung> findByUebungId(Long id);
}
