package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import java.util.List;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomSearchResponse {

    private String title;
    private Long chatRoomId;
    private String imgSrc;
    private Integer capability;
    private Integer currentUserCount;

    private List<ChatRoomHashtagResponse> hashtags;

    public static ChatRoomSearchResponse from(ChatRoomEntity chatRoom) {

        return ChatRoomSearchResponse.builder()
                .title(chatRoom.getTitle())
                .chatRoomId(chatRoom.getId())
                .imgSrc(chatRoom.getImgSrc())
                .capability(chatRoom.getCapability())
                .currentUserCount(chatRoom.getUserChatRooms().size())
                .hashtags(
                        chatRoom.getChatRoomHashtags().stream().map(ChatRoomHashtagResponse::from).toList())
                .build();
    }
}
