package com.ssafy.goumunity.domain.region.controller;

import com.ssafy.goumunity.domain.region.controller.request.RegionRegistRequest;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.region.service.RegionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid RegionRegistRequest regionRegistRequest) {
        regionService.save(regionRegistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
