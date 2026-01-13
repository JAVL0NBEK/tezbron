package com.example.bron.location;

import com.example.bron.location.dto.DistrictResponseDto;
import com.example.bron.location.dto.RegionResponseDto;
import java.util.List;

public interface RegionDistrictService {

  List<RegionResponseDto> getAll();

  String createRegionDistrict();

  List<DistrictResponseDto> getDistrictsByRegionId(
      Long regionId);

}
