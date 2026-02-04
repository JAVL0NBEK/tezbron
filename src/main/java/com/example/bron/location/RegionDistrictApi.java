package com.example.bron.location;

import com.example.bron.common.BaseResponse;
import com.example.bron.location.dto.DistrictResponseDto;
import com.example.bron.location.dto.RegionResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/region-districts")
@Tag(name = "Region Management APIs", description = "Endpoints for managing Region")
public interface RegionDistrictApi {

  @GetMapping("/regions")
  ResponseEntity<BaseResponse<List<RegionResponseDto>>> getAll();

  @PostMapping("/create")
  ResponseEntity<BaseResponse<String>> create();

  @GetMapping("/districts-by-region")
  ResponseEntity<BaseResponse<List<DistrictResponseDto>>> getDistrictsByRegionId(@RequestParam("regionId") Long regionId);

}
