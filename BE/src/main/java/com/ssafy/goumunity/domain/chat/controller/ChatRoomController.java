package com.ssafy.goumunity.domain.chat.controller;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.service.ChatRoomService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/chat-rooms")
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<Void> createChatRoom(
            @RequestPart("data") @Valid ChatRoomRequest.Create dto,
            @RequestPart("image") MultipartFile multipartFile,
            @AuthenticationPrincipal User user) {
        chatRoomService.createChatRoom(dto, multipartFile, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
