package com.university.gestionEtudiant.validator;

import com.university.gestionEtudiant.model.dto.PeriodeDto;
import com.university.gestionEtudiant.repository.AnneeAcademiqueRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PeriodeAnneeValidator implements ConstraintValidator<ValidPeriodeAnnee, PeriodeDto> {

    @Autowired
    private AnneeAcademiqueRepository anneeAcademiqueRepository;

    @Override
    public boolean isValid(PeriodeDto dto, ConstraintValidatorContext context) {
        // Sécurité contre les valeurs nulles
        if (dto == null || dto.dateDeb() == null || dto.dateFin() == null || dto.idAnnee() == null) {
            return true;
        }

        // 1. Récupérer l'année académique en base (ex: "2016-2017")
        return anneeAcademiqueRepository.findById(dto.idAnnee())
                .map(anneeEntity -> {
                    String anneeScolaire = anneeEntity.getAnnee(); // ex: "2016-2017"

                    if (anneeScolaire == null || !anneeScolaire.contains("-")) {
                        return false;
                    }

                    try {
                        // 2. Découper la chaîne "2016-2017" pour obtenir les deux années numériques
                        String[] parties = anneeScolaire.split("-");
                        int anneeDebutAutorisee = Integer.parseInt(parties[0].trim()); // 2016
                        int anneeFinAutorisee = Integer.parseInt(parties[1].trim());   // 2017

                        // 3. Extraire les années de vos dates saisies
                        int anneeDateDeb = dto.dateDeb().getYear(); // ex: 2016
                        int anneeDateFin = dto.dateFin().getYear(); // ex: 2016

                        // 4. Vérifier que l'année de dateDeb ET l'année de dateFin
                        // sont soit égales à l'année de début, soit à l'année de fin académique
                        boolean debValide = (anneeDateDeb == anneeDebutAutorisee || anneeDateDeb == anneeFinAutorisee);
                        boolean finValide = (anneeDateFin == anneeDebutAutorisee || anneeDateFin == anneeFinAutorisee);

                        return debValide && finValide;

                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        // En cas de mauvais format de la chaîne en base (ex: "2016/2017")
                        return false;
                    }
                })
                .orElse(false);
    }
}
