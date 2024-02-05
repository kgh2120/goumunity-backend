package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface ChatRoomService {
    void createChatRoom(ChatRoomRequest.Create dto, MultipartFile multipartFile, User user);

    void connectChatRoom(Long chatRoomId, User user);

    void exitChatRoom(Long chatRoomId, User user);

    Slice<ChatRoomSearchResponse> searchChatRoom(String keyword, Long time, Pageable pageable);

    Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable);

    boolean verifyAccessChat(Long chatRoom, User user);

    void disconnectChatRoom(Long chatRoomId, User user);

    Slice<ChatRoomUserResponse> findChatRoomUsers(
            Long chatRoomId, Pageable pageable, Long time, User user);

    void modifyChatRoom(
            Long chatRoomId, User user, ChatRoomRequest.Modify dto, MultipartFile multipartFile);

    MyChatRoomResponse findOneMyChatRoomByChatRoomId(Long chatRoomId, User user);
}
