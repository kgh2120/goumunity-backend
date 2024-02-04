package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.controller.request.MessageRequest;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatEntity;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatType;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import lombok.*;

public class MessageResponse {

    private String content;
    private String nickname;
    private String profileImageSrc;
    private Long userId;
    private ChatType chatType;
    private Long createdAt;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Previous {
        private String content;
        private String nickname;
        private String profileImageSrc;
        private Long userId;
        private ChatType chatType;
        private Long createdAt;
        private Boolean isCurrentUser;

        public Previous(ChatEntity chat, Long currentUserId) {
            UserEntity user = chat.getUser();
            userId = user.getId();
            nickname = user.getNickname();
            content = user.getNickname();
            profileImageSrc = user.getImgSrc();
            chatType = chat.getChatType();
            createdAt = chat.getCreatedAt().toEpochMilli();
            isCurrentUser = user.getId().equals(currentUserId);
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Live {
        private String content;
        private String nickname;
        private String profileImageSrc;
        private Long userId;
        private ChatType chatType;
        private Long createdAt;

        public static Live create(MessageRequest.Create create, User user) {
            return Live.builder()
                    .chatType(create.getChatType())
                    .content(create.getContent())
                    .nickname(user.getNickname())
                    .userId(user.getId())
                    .profileImageSrc(user.getImgSrc())
                    .build();
        }
    }
}
