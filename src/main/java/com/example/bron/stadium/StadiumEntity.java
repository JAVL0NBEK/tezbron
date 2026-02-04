package com.example.bron.stadium;

import com.example.bron.booking.BookingEntity;
import com.example.bron.common.FileResponseDto;
import com.example.bron.enums.StadiumDuration;
import com.example.bron.enums.StadiumType;
import com.example.bron.location.DistrictEntity;
import com.example.bron.location.RegionEntity;
import com.example.bron.match.MatchEntity;
import com.example.bron.auth.user.UserEntity;
import com.example.bron.stadium.dto.LocationDto;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id", nullable = false)
    private RegionEntity region;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictEntity district;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", nullable = false)
    private LocationDto location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StadiumType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StadiumDuration duration;

    private Integer capacity;
    private Double pricePerHour;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", name = "images")
    private List<FileResponseDto> images;

    private Boolean isActive;

    @OneToMany(mappedBy = "stadium")
    private List<BookingEntity> bookings;

    @OneToMany(mappedBy = "stadium")
    private List<MatchEntity> matches;
}
