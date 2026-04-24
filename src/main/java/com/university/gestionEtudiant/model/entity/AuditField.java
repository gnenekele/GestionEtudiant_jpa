package com.university.gestionEtudiant.model.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@MappedSuperclass   // ← Clé : JPA ne crée pas de table pour cette classe

public abstract class AuditField {


  private String createdBy;
  private LocalDateTime createdOn;
  private String modifiedBy;
  private LocalDateTime modifiedOn;




}
