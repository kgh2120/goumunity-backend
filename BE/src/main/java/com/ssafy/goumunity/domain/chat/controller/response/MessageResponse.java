package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.chat.ChatEntity;
import com.ssafy.goumunity.domain.chat.infra.chat.ChatType;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {

    private String content;
    private String nickname;
    private String profileImageSrc;
    private Long userId;
    private ChatType chatType;
    private Long createdAt;

    public MessageResponse(ChatEntity chat) {
        UserEntity user = chat.getUser();
        userId = user.getId();
        nickname = user.getNickname();
        content = user.getNickname();
        profileImageSrc = user.getImgSrc();
        chatType = chat.getChatType();
        createdAt = chat.getCreatedAt().toEpochMilli();
    }
}
