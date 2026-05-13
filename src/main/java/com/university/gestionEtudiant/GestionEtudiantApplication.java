package com.university.gestionEtudiant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GestionEtudiantApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionEtudiantApplication.class, args);
	}

}
