package com.example.bron.stadium;

import com.example.bron.booking.BookingEntity;
import com.example.bron.enums.StadiumType;
import com.example.bron.match.MatchEntity;
import com.example.bron.user.UserEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "stadiums")
@Getter
@Setter
public class StadiumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    private String description;

    @Column(columnDefinition = "jsonb", nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StadiumType type;

    private Integer capacity;
    private Double pricePerHour;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", name = "images")
    private List<String> images;

    @Column(columnDefinition = "jsonb")
    private String availabilitySlots;

    private Boolean isActive;

    @OneToMany(mappedBy = "stadium")
    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "stadium")
    private List<MatchEntity> matches;
}
