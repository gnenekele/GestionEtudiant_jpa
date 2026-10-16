package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.EmploiDuTempsDto;
import com.university.gestionEtudiant.model.entity.*;
import com.university.gestionEtudiant.model.mapper.EmploiDuTempsMapper;
import com.university.gestionEtudiant.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmploiDuTempsServiceImpl implements EmploiDuTempsService{

    private final MatiereRepository matiereRepos;
    private final ClasseRepository classeRepos;
    private final ProfesseurRepository professeurRepos;
    private final EmploiDuTempRepository etudiantRepos;
    private final EmploiDuTempsMapper emploiMapper;
            private  final EmploiDuTempRepository emploiRepos;
    private static final Logger log = LogUtils.getLogger(EmploiDuTemps.class);

    @Override
    public EmploiDuTempsDto getEmploiTempsById(Long id) {
        log.info("getEmploiTempsById  for {}",id);
        try {
            Optional<EmploiDuTemps> data = emploiRepos.findById(id);
            if (data.isPresent()) {
                return emploiMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getEmploiTempsById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }


    @Override
    public List<EmploiDuTempsDto> getAllEmploiTemps(Pageable pageable) {

        log.info("loading getAllEmploiTemps page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return emploiRepos.findAll(pageable).getContent().stream()
                    .map(emploiMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllEmploiTemps page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }


    @Override
    public EmploiDuTempsDto save(EmploiDuTempsDto dto) {
        log.info("save emploiDuTemps");
        if (dto.id() != null) {
            throw new BadRequestException(MessageEnum.ID_NOT_REQUIRED.getText());
        }
        try{
            // 1. Mapper le DTO → Entité (sans les cles etrangere)
            EmploiDuTemps emploi = emploiMapper.toEntity(dto);

            // 2. Charger cles etrangere depuis la base
            Matiere matiere = matiereRepos.findById(dto.idMatiere())
                    .orElseThrow(() -> new DataNotFoundException("Matiere non trouvée"));

            Classe classe = classeRepos.findById(dto.idClasse())
                    .orElseThrow(() -> new DataNotFoundException("classe non trouvée"));

            Professeur professeur = professeurRepos.findById(dto.idProfesseur())
                    .orElseThrow(() -> new DataNotFoundException("professeur non trouvée"));

            // 3. Associer la periode,la matiere à l'emploi
            emploi.setMatiere(matiere);
            emploi.setClasse(classe);
            emploi.setProfesseur(professeur);

            // 4. Sauvegarder
            return emploiMapper.toDto(emploiRepos.save(emploi));
        } catch (Exception e) {
            log.error("save emploiDuTemps failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public EmploiDuTempsDto update(EmploiDuTempsDto dto) {
        log.info("update emploiDuTemps");
        if (dto.id() == null) {
            throw new BadRequestException(MessageEnum.ID_REQUIRED.getText());  // ← Corrigé : ID_REQUIS pour update
        }
        try {
            Optional<EmploiDuTemps> existing = emploiRepos.findById(dto.id());
            if (existing.isPresent()) {
                EmploiDuTemps emploi = emploiMapper.toEntity(dto);

                // Charger l'année académique aussi pour l'update
                Matiere matiere = matiereRepos.findById(dto.idMatiere())
                        .orElseThrow(() -> new DataNotFoundException("Matiere non trouvée"));

                Classe classe = classeRepos.findById(dto.idClasse())
                        .orElseThrow(() -> new DataNotFoundException("classe non trouvée"));

                Professeur professeur = professeurRepos.findById(dto.idProfesseur())
                        .orElseThrow(() -> new DataNotFoundException("professeur non trouvée"));

                // 3. Associer la periode,la matiere à l'emploi
                emploi.setMatiere(matiere);
                emploi.setClasse(classe);
                emploi.setProfesseur(professeur);

            }
        } catch (Exception e) {
            log.error("update emploiDuTemps failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());

    }

    @Override
    public void deleteEmploiTemps(Long id) {

        // Check if data exists and throw an exception if not found
        Optional<EmploiDuTemps> data = emploiRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }

        try {
            emploiRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete emploiDuTemps failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }

    }
}
