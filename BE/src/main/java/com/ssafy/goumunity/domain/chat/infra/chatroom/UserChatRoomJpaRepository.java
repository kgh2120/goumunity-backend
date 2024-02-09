package com.ssafy.goumunity.domain.chat.infra.chatroom;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserChatRoomJpaRepository extends JpaRepository<UserChatRoomEntity, Long> {

    boolean existsByChatRoom_IdAndUser_Id(Long chatRoomId, Long userId);

    void deleteOneByChatRoom_IdAndUser_Id(Long chatRoomId, Long userId);

    Optional<UserChatRoomEntity> findOnyByUser_IdAndChatRoom_Id(Long userId, Long chatRoomId);

    @Modifying
    @Query("delete from UserChatRoomEntity u where u.user.id =:userId")
    void deleteAllByUserId(Long userId);
}
