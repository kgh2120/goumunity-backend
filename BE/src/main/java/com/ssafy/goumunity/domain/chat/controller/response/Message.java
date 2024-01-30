package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.ChatType;
import lombok.*;

public class Message {

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Request {
        private String content;
        private ChatType chatType;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Response {
        private String content;
        private String nickname;
        private String profileImageSrc;
        private Long userId;
        private ChatType chatType;
    }
}
