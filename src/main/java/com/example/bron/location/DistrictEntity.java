package com.example.bron.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "districts",
    uniqueConstraints = @UniqueConstraint(columnNames = "external_id")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** tashqi API dagi id */
  @Column(name = "external_id", nullable = false, unique = true)
  private Long externalId;

  private String name;

  @Column(name = "short_name")
  private String shortName;

  /** qaysi viloyat / shahar ga tegishli */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "region_id", nullable = false)
  private RegionEntity region;
}


