package com.ssafy.goumunity.domain.chat.controller.request;

import com.ssafy.goumunity.domain.chat.infra.chat.ChatType;
import lombok.*;

public class MessageRequest {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Create {
        private String content;
        private ChatType chatType;
    }
}
