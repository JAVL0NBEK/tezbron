package com.example.bron.location;

import com.example.bron.location.dto.DistrictResponseDto;
import com.example.bron.location.dto.RegionResponseDto;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegionComponent {

  private final RestTemplate restTemplate;
  private final RegionRepository regionRepository;
  private final DistrictRepository districtRepository;

  public void importRegions() {
    // 1. Viloyatlar
    String provincesUrl = "https://tasnif.joriy.uz/api/regions/provinces";
    RegionResponseDto[] regions = restTemplate.getForObject(provincesUrl, RegionResponseDto[].class);

    if (regions != null) {
      for (RegionResponseDto p : regions) {
        RegionEntity region = new RegionEntity();
        region.setExternalId(p.getId());
        region.setName(p.getName());
        region.setShortName(p.getShortName());
        regionRepository.save(region);

        // 2. Har bir viloyat uchun tumanlar
        importDistrictsByProvince(region);
      }
    }
  }

  private void importDistrictsByProvince(RegionEntity region) {
    String districtsUrl = "https://tasnif.joriy.uz/api/regions/districts?provinceId=" + region.getExternalId();
    DistrictResponseDto[] districts = restTemplate.getForObject(districtsUrl, DistrictResponseDto[].class);

    if (districts != null) {
      Arrays.stream(districts).forEach(d -> {
        DistrictEntity district = new DistrictEntity();
        district.setExternalId(d.getId());
        district.setName(d.getName());
        district.setShortName(d.getShortName());
        district.setRegion(region);
        districtRepository.save(district);
      });
    }
  }
}

