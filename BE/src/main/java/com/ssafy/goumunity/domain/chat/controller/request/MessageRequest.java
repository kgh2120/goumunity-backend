package com.ssafy.goumunity.domain.chat.controller.request;

import com.ssafy.goumunity.domain.chat.infra.chat.ChatType;
import com.ssafy.goumunity.domain.user.domain.User;
import lombok.*;

public class MessageRequest {
    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class Create {
        private String content;
        private ChatType chatType;

        public static Create enter(User user) {
            return Create.builder().chatType(ChatType.ENTER).build();
        }

        public static Create exit(User user) {
            return Create.builder().chatType(ChatType.EXIT).build();
        }
    }
}
