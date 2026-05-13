package com.university.gestionEtudiant.repository;
import com.university.gestionEtudiant.model.entity.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesseurRepository extends JpaRepository<Professeur,Long> {
    boolean existsByCodeProf(String codeProf);

    boolean existsByMatriculeProf(String matriculeProf);

    boolean existsByCodeProfAndIdNot(String codeProf, Long id);

    boolean existsByMatriculeProfAndIdNot(String matriculeProf, Long id);
}