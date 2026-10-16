package com.university.gestionEtudiant.repository;


import com.university.gestionEtudiant.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Long> {
    boolean existsByNomParentAndPrenomParentAndEmail(String nomParent, String prenomParent, String email);
    boolean existsByNomParentAndPrenomParentAndEmailAndIdNot(String nomParent, String prenomParent, String email,Long id);
}
