package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.AbsenceDto;
import com.university.gestionEtudiant.model.entity.Absence;

import com.university.gestionEtudiant.model.mapper.AbsenceMapper;
import com.university.gestionEtudiant.repository.AbsenceRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbsenceServiceImpl implements AbsenceService{
    private final AbsenceRepository absenceRepos;
    private final AbsenceMapper absenceMapper;
    private static final Logger log = LogUtils.getLogger(Absence.class);

    @Override
    public AbsenceDto getAbsenceById(Long id) {
        log.info("getAbsenceById  for {}", id);
        try {
            Optional<Absence> data = absenceRepos.findById(id);
            if (data.isPresent()) {
                return absenceMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getAbsenceById  failed:", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());

    }

    @Override
    public List<AbsenceDto> getAllAbsence(Pageable pageable) {
        log.info("loading getAllAbsence page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try{
            return absenceRepos.findAll(pageable).getContent().stream()
                    .map(absenceMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllAbsence page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public AbsenceDto save(AbsenceDto dto) {
        log.info("save Absence ");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());}

        try {
            return absenceMapper.toDto(absenceRepos.save(absenceMapper.toEntity(dto)));

        } catch (Exception e) {
            log.error("save Absence  failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
    }

    @Override
    public AbsenceDto update(AbsenceDto dto) {
        log.info("update Absence");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }

        try{
            Optional<Absence> absence = absenceRepos.findById(dto.id());
            if (absence.isPresent()){
                return absenceMapper.toDto(absenceRepos.save(absenceMapper.toEntity(dto)));
            }
        }
        catch (Exception e) {
            log.error("update Absence failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR,e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }



    @Override
    public void deleteAbsence(Long id) {
        // Check if data exists and throw an exception if not found
        Optional<Absence> data = absenceRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            absenceRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete Absence failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }}
