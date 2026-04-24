package com.university.gestionEtudiant.repository;

import com.university.gestionEtudiant.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
