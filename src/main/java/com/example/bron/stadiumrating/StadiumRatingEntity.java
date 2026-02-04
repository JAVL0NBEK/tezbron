package com.example.bron.stadiumrating;

import com.example.bron.auth.user.UserEntity;
import com.example.bron.stadium.StadiumEntity;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "stadium_ratings",
    uniqueConstraints = @UniqueConstraint(name = "uk_user_stadium", columnNames = {"user_id","stadium_id"}))
public class StadiumRatingEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stadium_id", nullable = false)
  private StadiumEntity stadium;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private UserEntity user;

  @Column(name = "rating", nullable = false)
  private Integer rating; // 1–5

  @Column(name = "comment")
  private String comment;

}
