package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.NoteDto;

import com.university.gestionEtudiant.model.entity.Note;
import com.university.gestionEtudiant.model.mapper.NoteMapper;
import com.university.gestionEtudiant.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepos;
    private final NoteMapper noteMapper;
    private static final Logger log = LogUtils.getLogger(Note.class);


    @Override
    public List<NoteDto> getAllNote(Pageable pageable) {
        log.info("loading getAllNote page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try{
            return noteRepos.findAll(pageable).getContent().stream()
                    .map(noteMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllNote page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }

    }

    @Override
    public NoteDto getNoteById(Long id) {
        log.info("getNoteById  for {}", id);
        try {
            Optional<Note> data = noteRepos.findById(id);
            if (data.isPresent()) {
                return noteMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getNoteById  failed:", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());

    }

    @Override
    public NoteDto save(NoteDto dto) {
        log.info("save Note");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());}

        try {
            return noteMapper.toDto(noteRepos.save(noteMapper.toEntity(dto)));

        } catch (Exception e) {
            log.error("save Note failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public NoteDto update(NoteDto dto) {
        log.info("update Note");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }

        try{
            Optional<Note> niveau = noteRepos.findById(dto.id());
            if (niveau.isPresent()){
                return noteMapper.toDto(noteRepos.save(noteMapper.toEntity(dto)));
            }
        }
        catch (Exception e) {
            log.error("update Note failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public void deleteNote(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Note> data = noteRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            noteRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete deleteNote failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

}
