package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.ChatEntity;
import com.ssafy.goumunity.domain.chat.infra.ChatType;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import lombok.*;

public class Message {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Request {
        private String content;
        private ChatType chatType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String content;
        private String nickname;
        private String profileImageSrc;
        private Long userId;
        private ChatType chatType;
        private Long createdAt;

        public Response(ChatEntity chat) {
            UserEntity user = chat.getUser();
            userId = user.getId();
            nickname = user.getNickname();
            content = user.getNickname();
            profileImageSrc = user.getImgSrc();
            chatType = chat.getChatType();
            createdAt = chat.getCreatedAt().toEpochMilli();
        }
    }
}
