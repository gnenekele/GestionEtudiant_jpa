package com.university.gestionEtudiant.validator;

import com.university.gestionEtudiant.model.dto.PeriodeDto;
import com.university.gestionEtudiant.model.entity.AnneeAcademique;
import com.university.gestionEtudiant.repository.AnneeAcademiqueRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;


@Component
public class PeriodeValidator implements ConstraintValidator<ValidPeriode, PeriodeDto> {

    @Autowired
    private AnneeAcademiqueRepository anneeRepos;

    @Override
    public boolean isValid(PeriodeDto dto, ConstraintValidatorContext context) {
        // Si le DTO ou les dates indispensables sont nulles, on laisse les annotations @NotNull gérer
        if (dto == null || dto.dateDeb() == null || dto.dateFin() == null || dto.idAnnee() == null) {
            return true;
        }

        try {
            // 1. Récupérer l'année académique associée pour lire le libellé textuel
            AnneeAcademique annee = anneeRepos.findById(dto.idAnnee()).orElse(null);
            if (annee == null || annee.getAnnee() == null || !annee.getAnnee().contains("-")) {
                remplacerMessageErreur(context, "Année académique introuvable ou format incorrect.");
                return false;
            }

            // 2. Découper le libellé textuel (Ex: "1998-1999" -> 1998 et 1999)
            String[] parties = annee.getAnnee().split("-");
            int anneeDebutScolaire = Integer.parseInt(parties[0].trim());
            int anneeFinScolaire = Integer.parseInt(parties[1].trim());

            // 3. Extraire l'année des LocalDate saisies
            int anneeSaisieDeb = dto.dateDeb().getYear();
            int anneeSaisieFin = dto.dateFin().getYear();

            // 4. Vérification de la cohérence de l'année scolaire
            if (anneeSaisieDeb != anneeDebutScolaire && anneeSaisieDeb != anneeFinScolaire) {
                remplacerMessageErreur(context, "La date de début (" + anneeSaisieDeb + ") ne correspond pas à l'année académique " + annee.getAnnee());
                return false;
            }

            if (anneeSaisieFin != anneeDebutScolaire && anneeSaisieFin != anneeFinScolaire) {
                remplacerMessageErreur(context, "La date de fin (" + anneeSaisieFin + ") ne correspond pas à l'année académique " + annee.getAnnee());
                return false;
            }

            // 5. Vérification de la durée de 3 mois (90 jours)
            long jours = ChronoUnit.DAYS.between(dto.dateDeb(), dto.dateFin());
            if (jours != 90) {
                remplacerMessageErreur(context, "La période doit faire exactement 3 mois (90 jours). Reçu : " + jours + " jours.");
                return false;
            }

            return true; // Tout est valide !

        } catch (Exception e) {
            remplacerMessageErreur(context, "Erreur lors de la validation des dates : " + e.getMessage());
            return false;
        }
    }

    // Méthode utilitaire pour envoyer le bon message dynamique à Spring
    private void remplacerMessageErreur(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }


    //    @Override
//    public boolean isValid(Periode periode, ConstraintValidatorContext context) {
//        LocalDate debut = periode.getDateDeb();
//        LocalDate fin = periode.getDateFin();
//
//        if (debut == null || fin == null) {
//            return true; // Laisse @NotNull gérer
//        }
//
//        // ✅ Vérifie que dateFin = dateDeb + 3 mois (plus fiable)
//        LocalDate attendu = debut.plusMonths(3);
//        return fin.isEqual(attendu);
//    }
}
