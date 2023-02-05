package com.haw.srs.customerservice.repositories;

import com.haw.srs.customerservice.entities.Uebung;
import com.haw.srs.customerservice.entities.UebungForTrainingsplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for all excersises
 */
@Repository
public interface UebungForTrainingsplanRepository extends JpaRepository<UebungForTrainingsplan, Long> {
    Optional<UebungForTrainingsplan> findByUebungForTrainingsPlanId(Long id);
}
