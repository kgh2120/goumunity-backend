package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.domain.Chat;
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
    @GeneratedValue
    private Long id;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_type")
    private ChatType chatType;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne
    private ChatRoomEntity chatRoomEntity;

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
                .chatRoom(this.chatRoomEntity.to())
                .user(this.user.toModel())
                .build();
    }

    public static ChatEntity from(Chat chat) {
        ChatEntityBuilder chatEntityBuilder =
                ChatEntity.builder()
                        .id(chat.getId())
                        .content(chat.getContent())
                        .chatType(chat.getChatType())
                        .createdAt(chat.getCreatedAt())
                        .updatedAt(chat.getUpdatedAt());

        if (chat.getCreatedAt() != null) chatEntityBuilder.createdAt(chat.getCreatedAt());
        if (chat.getUpdatedAt() != null) chatEntityBuilder.updatedAt(chat.getUpdatedAt());

        return chatEntityBuilder.build();
    }
}
