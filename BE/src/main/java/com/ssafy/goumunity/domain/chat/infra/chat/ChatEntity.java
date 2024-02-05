package com.ssafy.goumunity.domain.chat.infra.chat;

import com.ssafy.goumunity.domain.chat.domain.Chat;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chat")
@Entity
@Getter
@Builder
public class ChatEntity {

    @Id
    @Column(name = "chat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_type")
    private ChatType chatType;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne
    private ChatRoomEntity chatRoom;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;

    public Chat to() {
        return Chat.builder()
                .id(this.id)
                .content(this.content)
                .chatType(this.chatType)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .chatRoomId(this.chatRoom.getId())
                .userId(this.user.getId())
                .build();
    }

    public static ChatEntity from(Chat chat) {

        return ChatEntity.builder()
                .id(chat.getId())
                .content(chat.getContent())
                .chatType(chat.getChatType())
                .createdAt(chat.getCreatedAt())
                .updatedAt(chat.getUpdatedAt())
                .chatRoom(ChatRoomEntity.chatRoomEntityOnlyWithId(chat.getChatRoomId()))
                .user(UserEntity.userEntityOnlyWithId(chat.getUserId()))
                .createdAt(chat.getCreatedAt())
                .updatedAt(chat.getUpdatedAt())
                .build();
    }
}
