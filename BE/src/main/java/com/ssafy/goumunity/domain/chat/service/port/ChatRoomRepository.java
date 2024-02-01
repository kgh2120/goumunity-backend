package com.ssafy.goumunity.domain.chat.service.port;

import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomSearchResponse;
import com.ssafy.goumunity.domain.chat.controller.response.ChatRoomUserResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.domain.UserChatRoom;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRoomRepository {
    void save(ChatRoom chatRoom);

    Optional<ChatRoom> findOneByChatRoomId(Long chatRoomId);

    boolean isAlreadyJoinedUser(Long chatRoomId, Long userId);

    boolean isExistChatRoom(Long chatRoomId);

    void connectChatRoom(Long chatRoomId, Long userId);

    void deleteChatRoom(ChatRoom chatRoom);

    void exitChatRoom(Long chatRoomId, Long userId);

    Slice<ChatRoomSearchResponse> searchChatRoom(String keyword, Long time, Pageable pageable);

    Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable);

    Optional<UserChatRoom> findOneUserChatRoomByUserIdAndChatRoomId(Long userId, Long chatRoomId);

    void disconnectChatRoom(UserChatRoom userChatRoom);

    Slice<ChatRoomUserResponse> findChatRoomUsers(
            Long chatRoomRepository, Pageable pageable, Long time, Long userId);
}
