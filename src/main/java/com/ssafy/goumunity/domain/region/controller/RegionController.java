package com.ssafy.goumunity.domain.region.controller;

import com.ssafy.goumunity.domain.region.controller.request.RegionRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.controller.response.RegionsResponse;
import com.ssafy.goumunity.domain.region.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    @Cacheable(value = "regions", cacheManager = "cfCacheManager")
    @GetMapping
    public ResponseEntity<RegionsResponse> findAllCf() {
        return ResponseEntity.ok(RegionsResponse.of(regionService.findAll()));
    }

    @GetMapping("/{regionId}")
    public ResponseEntity<RegionResponse> findOneByRegionId(@PathVariable Long regionId) {
        return ResponseEntity.ok(regionService.findOneByRegionId(regionId));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid RegionRequest regionRequest) {
        regionService.save(regionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
