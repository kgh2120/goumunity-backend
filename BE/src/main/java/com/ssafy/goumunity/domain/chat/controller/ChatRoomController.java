package com.ssafy.goumunity.domain.chat.controller;

import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.service.ChatRoomService;
import com.ssafy.goumunity.domain.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PostMapping("/{chatRoomId}")
    public ResponseEntity<Void> connectChatRoom(
            @PathVariable Long chatRoomId, @AuthenticationPrincipal User user) {
        chatRoomService.connectChatRoom(chatRoomId, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/search")
    public ResponseEntity<SliceResponse<ChatRoomSearchResponse>> searchChatRoom(
            @RequestParam String keyword, @RequestParam Long time, Pageable pageable) {
        Slice<ChatRoomSearchResponse> response =
                chatRoomService.searchChatRoom(keyword, time, pageable);
        return ResponseEntity.ok(SliceResponse.from(response.getContent(), response.hasNext()));
    }
}
