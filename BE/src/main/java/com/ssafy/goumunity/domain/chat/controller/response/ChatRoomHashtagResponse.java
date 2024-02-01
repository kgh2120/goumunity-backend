package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.ChatRoomHashtagEntity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomHashtagResponse {

    private String name;
    private Integer sequence;

    public static ChatRoomHashtagResponse from(ChatRoomHashtagEntity chatRoomHashtag) {
        return ChatRoomHashtagResponse.builder()
                .name(chatRoomHashtag.getHashtag().getName())
                .sequence(chatRoomHashtag.getSequence())
                .build();
    }
}
