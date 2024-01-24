package com.ssafy.goumunity.domain.region.controller;

import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.service.RegionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public ResponseEntity<List<RegionResponse>> findAll() {
        return ResponseEntity.ok(regionService.findAll());
    }

    @GetMapping("/{regionId}")
    public ResponseEntity<RegionResponse> findOneByRegionId(@PathVariable Long regionId) {
        return ResponseEntity.ok(regionService.findOneByRegionId(regionId));
    }
}
