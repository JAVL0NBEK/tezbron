package com.example.bron.location;

import com.example.bron.location.dto.DistrictResponseDto;
import com.example.bron.location.dto.RegionResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionDistrictServiceImpl implements RegionDistrictService {
  private final RegionRepository regionRepository;
  private final DistrictRepository districtRepository;
  private final RegionComponent regionComponent;

  @Override
  public List<RegionResponseDto> getAll() {
    return regionRepository.getAll();
  }

  @Override
  public String createRegionDistrict() {
    try {
      regionComponent.importRegions();
      return "Success";

    } catch (Exception e) {
      log.error(e.getMessage());
      return e.getMessage();
    }
  }

  @Override
  public List<DistrictResponseDto> getDistrictsByRegionId(Long regionId) {
    return districtRepository.findDistrictByRegionId(regionId);
  }
}
