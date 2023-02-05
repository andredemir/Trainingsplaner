package com.haw.srs.customerservice.repositories;

import com.haw.srs.customerservice.entities.Trainingsplan;
import com.haw.srs.customerservice.entities.Uebung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for all excersises
 */
@Repository
public interface TrainingsplanRepository extends JpaRepository<Trainingsplan, Long> {
    Optional<Trainingsplan> findByTrainingsplanId(Long id);
}
