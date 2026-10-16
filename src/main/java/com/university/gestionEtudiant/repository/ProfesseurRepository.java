package com.university.gestionEtudiant.repository;
import com.university.gestionEtudiant.model.entity.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesseurRepository extends JpaRepository<Professeur,Long> {

    boolean existsByNomProfAndPrenomProfAndEmail(String nomProf,String prenomProf,String email);

   boolean existsByNomProfAndPrenomProfAndEmailAndIdNot(String nomProf,String prenomProf,String email, Long id);
}