package com.university.gestionEtudiant.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Si vous utilisez Spring Security :
        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // return Optional.ofNullable(auth != null ? auth.getName() : "System");

        return Optional.of("Admin_User"); // Valeur par défaut pour tester
    }
}
