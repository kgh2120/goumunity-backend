package com.ssafy.goumunity.domain.user.controller;

import com.ssafy.goumunity.domain.user.service.NicknameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nicknames")
public class NicknameController {

    private final NicknameService nicknameService;

    @GetMapping
    public ResponseEntity<String> createRandomNickname() {
        return ResponseEntity.ok().body(nicknameService.createRandomNickname());
    }
}
