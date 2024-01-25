package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.web.multipart.MultipartFile;

public interface ChatRoomService {
    void createChatRoom(ChatRoomRequest.Create dto, MultipartFile multipartFile, User user);
}
