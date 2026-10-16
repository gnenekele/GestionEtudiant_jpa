package com.university.gestionEtudiant.service;

import com.university.gestionEtudiant.model.entity.EtudiantSequence;
import com.university.gestionEtudiant.repository.EtudiantSequenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatriculeGeneratorService {

    private final EtudiantSequenceRepository sequenceRepository;

    @Transactional
    public String genererMatricule() {
        int anneeCourante = LocalDate.now().getYear();

        Optional<EtudiantSequence> optionalSequence = sequenceRepository.findByAnnee(anneeCourante);
        EtudiantSequence sequence;
        int nouveauNumero;

        if (optionalSequence.isPresent()) {
            sequence = optionalSequence.get();
            nouveauNumero = sequence.getDernierNumero() + 1;
            sequence.setDernierNumero(nouveauNumero);
        } else {
            sequence = EtudiantSequence.builder()
                    .annee(anneeCourante)
                    .dernierNumero(1)
                    .build();
            nouveauNumero = 1;
        }

        sequenceRepository.save(sequence);
        String matricule = String.format("ETU%d%03d", anneeCourante, nouveauNumero);
        log.info("Matricule généré : {}", matricule);
        return matricule;
    }

    @Transactional
    public String genererCodeEtudiant(String nomEtudiant, String prenomParent) {
        // 1. Extraction des trigrammes (ex: "DAO", "CEL")
        String prefixeNomEtu = extraireTrigramme(nomEtudiant);
        String prefixePreParent = extraireTrigramme(prenomParent);
        String prefixeFinal = prefixeNomEtu + prefixePreParent; // ex: "DAO_CEL"

        // 2. Génération d'un identifiant numérique unique basé sur le texte
        // Math.abs évite les nombres négatifs
        int codeNumeriqueIdentifiant = Math.abs(prefixeFinal.hashCode());

        // 3. Récupération ou création de la séquence en utilisant la colonne 'annee'
        Optional<EtudiantSequence> optionalSequence = sequenceRepository.findByAnnee(codeNumeriqueIdentifiant);
        EtudiantSequence sequence;
        int nouveauNumero;

        if (optionalSequence.isPresent()) {
            sequence = optionalSequence.get();
            nouveauNumero = sequence.getDernierNumero() + 1;
            sequence.setDernierNumero(nouveauNumero);
        } else {
            sequence = EtudiantSequence.builder()
                    .annee(codeNumeriqueIdentifiant)
                    .dernierNumero(1)
                    .build();
            nouveauNumero = 1;
        }

        sequenceRepository.save(sequence);

        // 4. Construction du code final textuel (ex: DAO_CEL_1)
        String codeGenere = String.format("%s%d", prefixeFinal, nouveauNumero);
        log.info("Code étudiant généré : {}", codeGenere);
        return codeGenere;
    }

    private String extraireTrigramme(String texte) {
        if (texte == null || texte.trim().isEmpty()) {
            return "XXX";
        }
        String nettoye = Normalizer.normalize(texte, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toUpperCase()
                .replaceAll("[^A-Z]", "");

        if (nettoye.length() >= 3) {
            return nettoye.substring(0, 3);
        } else {
            return String.format("%-3s", nettoye).replace(' ', 'X');
        }
    }

    @Transactional
    public String genererMatriculeProfesseur() {
        // On utilise une constante fixe (ex: 9999) pour stocker la séquence des profs dans la table
        // pour ne pas la mélanger avec les années des étudiants (ex: 2026)
        int codeSequenceProf = 9999;

        Optional<EtudiantSequence> optionalSequence = sequenceRepository.findByAnnee(codeSequenceProf);
        EtudiantSequence sequence;
        int nouveauNumero;

        if (optionalSequence.isPresent()) {
            sequence = optionalSequence.get();
            nouveauNumero = sequence.getDernierNumero() + 1;
            sequence.setDernierNumero(nouveauNumero);
        } else {
            // Si c'est le tout premier professeur, on démarre à 82001
            sequence = EtudiantSequence.builder()
                    .annee(codeSequenceProf)
                    .dernierNumero(82000)
                    .build();
            nouveauNumero = 82000;
        }

        sequenceRepository.save(sequence);

        // Formate le résultat pour obtenir "PRF-82001", "PRF-82002", etc.
        String matriculeProf = String.format("PRF-%d", nouveauNumero);
        log.info("Matricule Professeur généré : {}", matriculeProf);

        return matriculeProf;
    }

}
