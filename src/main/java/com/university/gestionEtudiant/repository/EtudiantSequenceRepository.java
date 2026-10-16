package com.university.gestionEtudiant.repository;


import com.university.gestionEtudiant.model.entity.EtudiantSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtudiantSequenceRepository extends JpaRepository<EtudiantSequence, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EtudiantSequence> findByAnnee(Integer annee);
}