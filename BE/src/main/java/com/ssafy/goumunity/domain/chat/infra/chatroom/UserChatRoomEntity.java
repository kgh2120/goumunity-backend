package com.ssafy.goumunity.domain.chat.infra.chatroom;

import com.ssafy.goumunity.domain.chat.domain.UserChatRoom;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_chat_room")
@Entity
public class UserChatRoomEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_chat_room_id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoomEntity chatRoom;

    @Column(name = "last_access_time")
    private Instant lastAccessTime;

    public static UserChatRoomEntity create(UserEntity userEntity, ChatRoomEntity chatRoom) {
        return UserChatRoomEntity.builder()
                .chatRoom(chatRoom)
                .user(userEntity)
                .lastAccessTime(Instant.now())
                .build();
    }

    public static UserChatRoomEntity from(UserChatRoom userChatRoom) {
        return UserChatRoomEntity.builder()
                .id(userChatRoom.getUserChatRoomId())
                .chatRoom(ChatRoomEntity.chatRoomEntityOnlyWithId(userChatRoom.getChatRoomId()))
                .user(UserEntity.userEntityOnlyWithId(userChatRoom.getUserId()))
                .lastAccessTime(userChatRoom.getLastAccessTime())
                .build();
    }

    public UserChatRoom to() {
        return UserChatRoom.builder()
                .userChatRoomId(id)
                .userId(user.getId())
                .userChatRoomId(chatRoom.getId())
                .build();
    }
}
