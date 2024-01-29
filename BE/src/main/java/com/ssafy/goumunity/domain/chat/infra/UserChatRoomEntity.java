package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
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

    public static UserChatRoomEntity create(UserEntity userEntity, ChatRoomEntity chatRoom) {
        return UserChatRoomEntity.builder().chatRoom(chatRoom).user(userEntity).build();
    }
}
