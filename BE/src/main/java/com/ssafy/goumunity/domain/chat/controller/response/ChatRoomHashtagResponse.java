package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.ChatRoomHashtagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomHashtagResponse {

    private String name;
    private int sequence;

    public static ChatRoomHashtagResponse from(ChatRoomHashtagEntity chatRoomHashtag) {
        return ChatRoomHashtagResponse.builder()
                .name(chatRoomHashtag.getHashtag().getName())
                .sequence(chatRoomHashtag.getSequence())
                .build();
    }
}
