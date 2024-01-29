package com.ssafy.goumunity.domain.chat.controller;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.service.ChatRoomService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/{chatRoomId}")
    public ResponseEntity<Void> connectChatRoom(
            @PathVariable Long chatRoomId, @AuthenticationPrincipal User user) {
        chatRoomService.connectChatRoom(chatRoomId, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> disconnectChatRoom(
            @PathVariable Long chatRoomId, @AuthenticationPrincipal User user) {
        chatRoomService.disconnectChatRoom(chatRoomId, user);
        return ResponseEntity.ok().build();
    }
}
