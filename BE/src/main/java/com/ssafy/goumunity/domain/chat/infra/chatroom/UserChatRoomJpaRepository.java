package com.ssafy.goumunity.domain.chat.infra.chatroom;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserChatRoomJpaRepository extends JpaRepository<UserChatRoomEntity, Long> {

    boolean existsByChatRoom_IdAndUser_Id(Long chatRoomId, Long userId);

    void deleteOneByChatRoom_IdAndUser_Id(Long chatRoomId, Long userId);

    Optional<UserChatRoomEntity> findOnyByUser_IdAndChatRoom_Id(Long userId, Long chatRoomId);
}
