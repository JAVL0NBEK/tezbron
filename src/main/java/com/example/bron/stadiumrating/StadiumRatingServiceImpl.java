package com.example.bron.stadiumrating;

import com.example.bron.auth.user.UserRepository;
import com.example.bron.exception.NotFoundException;
import com.example.bron.stadium.StadiumRepository;
import com.example.bron.stadiumrating.dto.StadiumRatingRequestDto;
import com.example.bron.stadiumrating.dto.StadiumRatingResponseDto;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StadiumRatingServiceImpl implements StadiumRatingService {
  private final StadiumRatingRepository stadiumRatingRepository;
  private final StadiumRepository stadiumRepository;
  private final UserRepository  userRepository;
  private final StadiumRatingMapper stadiumRatingMapper;

  @Override
  @Transactional
  public StadiumRatingResponseDto createStadiumRating(StadiumRatingRequestDto dto) {
    var stadium = stadiumRepository.findById(dto.getStadiumId()).orElseThrow(() -> new NotFoundException("stadium_not_found",
        List.of(dto.getUserId().toString())));

    var user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("owner_not_found",
        List.of(dto.getUserId().toString())));

    // Avval tekshiramiz user oldin baho berganmi?
    var existing = stadiumRatingRepository.findByUserIdAndStadiumId(
        dto.getUserId(),
        dto.getStadiumId()
    );

    if (existing == null) {
      // CREATE
      existing = new StadiumRatingEntity();
      existing.setStadium(stadium);
      existing.setUser(user);
    }

    // UPDATE or CREATE
    existing.setRating(dto.getRating());
    existing.setComment(dto.getComment());

    var saved = stadiumRatingRepository.save(existing);
    saved.setStadium(stadium);
    saved.setUser(user);
    return stadiumRatingMapper.toDto(saved);
  }
}
