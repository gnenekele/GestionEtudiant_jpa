package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.dto.NoteDto;
import org.springframework.data.domain.Pageable;



import java.util.List;

public interface NoteService {
    NoteDto getNoteById (Long id);
    List<NoteDto> getAllNote(Pageable pageable);
    NoteDto save (NoteDto dto);
    NoteDto update (NoteDto dto);

    void deleteNote (Long id);
}
