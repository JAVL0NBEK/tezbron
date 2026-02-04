package com.example.bron.location;

import com.example.bron.common.BaseResponse;
import com.example.bron.location.dto.DistrictResponseDto;
import com.example.bron.location.dto.RegionResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegionDistrictController implements RegionDistrictApi{
  private final RegionDistrictService regionDistrictService;

  @Override
  public ResponseEntity<BaseResponse<List<RegionResponseDto>>> getAll() {
    var response = regionDistrictService.getAll();
    return ResponseEntity.ok(BaseResponse.ok(response));
  }

  @Override
  public ResponseEntity<BaseResponse<String>> create() {
    return null;
  }

  @Override
  public ResponseEntity<BaseResponse<List<DistrictResponseDto>>> getDistrictsByRegionId(
      Long regionId) {
    var response = regionDistrictService.getDistrictsByRegionId(regionId);
    return ResponseEntity.ok(BaseResponse.ok(response));
  }
}
