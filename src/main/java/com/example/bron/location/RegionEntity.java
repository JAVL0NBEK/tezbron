package com.example.bron.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "regions",
    uniqueConstraints = @UniqueConstraint(columnNames = "external_id"))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "external_id", nullable = false, unique = true)
  private Long externalId;

  private String name;

  @Column(name = "short_name")
  private String shortName;
}

