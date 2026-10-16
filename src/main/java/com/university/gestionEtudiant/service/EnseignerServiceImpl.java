package com.university.gestionEtudiant.service;

import ci.tresorpublic.commons.error.AppException;
import ci.tresorpublic.commons.error.ErrorCode;
import ci.tresorpublic.commons.error.Errors;
import ci.tresorpublic.commons.logging.LogUtils;
import com.university.gestionEtudiant.api.BadRequestException;
import com.university.gestionEtudiant.api.DataNotFoundException;
import com.university.gestionEtudiant.api.MessageEnum;
import com.university.gestionEtudiant.model.dto.EnseignerFormDto;
import com.university.gestionEtudiant.model.dto.EnseignerTableDto;
import com.university.gestionEtudiant.model.entity.Enseigner;
import com.university.gestionEtudiant.model.entity.Matiere;
import com.university.gestionEtudiant.model.entity.Professeur;
import com.university.gestionEtudiant.model.mapper.EnseignerMapper;
import com.university.gestionEtudiant.repository.EnseignerRepository;
import com.university.gestionEtudiant.repository.MatiereRepository;
import com.university.gestionEtudiant.repository.ProfesseurRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnseignerServiceImpl implements EnseignerService {
    private final MatiereRepository matiereRepos;
    private final ProfesseurRepository professeurRepos;
    private  final EnseignerRepository enseignerRepos;
    private final EnseignerMapper enseignerMapper;
    private static final Logger log = LogUtils.getLogger(Professeur.class);


    @Override
    public List<EnseignerTableDto> getAllEnseigner(Pageable pageable) {
        log.info("loading getAllEnseigner page {} (size={})", pageable.getPageNumber(), pageable.getPageSize());
        try {
            return enseignerRepos.findAll(pageable).getContent().stream()
                    .map(enseignerMapper::toDto)
                    .toList();
        } catch (Exception e) {
            log.error("loading getAllEnseigner page failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public EnseignerTableDto getEnseignerById(Long id) {
        log.info("getEnseignerById  for {}",id);
        try {
            Optional<Enseigner> data = enseignerRepos.findById(id);
            if (data.isPresent()) {
                return enseignerMapper.toDto(data.get());
            }
        } catch (Exception e) {
            log.info("getEnseignerById  failed:",e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
        throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
    }

    @Override
    public EnseignerTableDto save(EnseignerFormDto dto) {
        log.info("save Enseigner");
        try {
            // 1. Récupérer le Professeur et la Matière via leur ID présent dans le DTO
            Long professeurId = dto.idProf();
            Professeur professeur = professeurRepos.findById(professeurId)
                    .orElseThrow(() -> new EntityNotFoundException("Professeur non trouvé"));
            Long matiereId = dto.idMatiere();
            Matiere matiere = matiereRepos.findById(matiereId)
                    .orElseThrow(() -> new EntityNotFoundException("Matière non trouvée"));

            // 3. Construire l'entité Enseigner SANS le codeEnseigner (géré par @PrePersist)
            Enseigner enseigner = Enseigner.builder()
                    .professeur(professeur)
                    .matiere(matiere)
                    .build();

            // 4. Sauvegarder en BDD (génération automatique du codeEnseigner ici)
            Enseigner savedEnseigner = enseignerRepos.save(enseigner);

            // 5. Convertir l'entité sauvegardée en DTO de retour via votre mapper
            return enseignerMapper.toDto(savedEnseigner);

        } catch (Exception e) {
            log.error("Erreur lors de l'enregistrement de la relation Enseigner", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }

    @Override
    public EnseignerTableDto update(Long id, EnseignerFormDto dto) {
        log.info("update Enseigner");
        // 1. Récupérer Enseigner existant
        Enseigner existing =enseignerRepos.findById(id).orElseThrow(()
                -> Errors.notFound(MessageEnum.DATA_NOT_FOUND.getText(), id.toString()));


        Professeur  prof = existing.getProfesseur();
        if (dto.idProf()!= null && (prof == null || !dto.idProf().equals(prof.getId()))){
            prof = professeurRepos.findById(dto.idProf()).orElseThrow(()
                    -> new BadRequestException("Enseigner avec l'identifiant " + dto.idProf() + " n'existe pas."));
        }
        Matiere matiere = existing.getMatiere();
        if (dto.idMatiere()!= null && (matiere == null || !dto.idMatiere().equals(matiere.getId()))){
            matiere =matiereRepos.findById(dto.idMatiere()).orElseThrow(()
                    -> new BadRequestException("Enseigner avec l'identifiant " + dto.idProf() + " n'existe pas."));
        }
        try{
            // 2. Reconstruction de l'entité avec préservation du matricule et du code
            Enseigner enseignerEntity = Enseigner.builder()
                    .id(id)
                    .professeur(prof)
                    .matiere(matiere)
                    .build();
            Enseigner enseigner = enseignerRepos.save(enseignerEntity);
            return enseignerMapper.toDto(enseigner);
        }catch (Exception e) {
            log.error("update etudiant failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }



    @Override
    public void deleteEnseigner(Long id) {

        Optional<Enseigner>data = enseignerRepos.findById(id);
        if (data.isEmpty()) {
            throw new DataNotFoundException(MessageEnum.DATA_NOT_FOUND.getText());
        }
        try {
            enseignerRepos.deleteById(id);
        } catch (Exception e) {
            log.error("delete Enseigner failed: ", e);
            throw new AppException(ErrorCode.INTERNAL_ERROR, e.getMessage());
        }
    }
}
