package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface ChatRoomService {
    void createChatRoom(ChatRoomRequest.Create dto, MultipartFile multipartFile, User user);

    void connectChatRoom(Long chatRoomId, User user);

    void disconnectChatRoom(Long chatRoomId, User user);

    Slice<ChatRoomSearchResponse> searchChatRoom(String keyword, Long time, Pageable pageable);
}
