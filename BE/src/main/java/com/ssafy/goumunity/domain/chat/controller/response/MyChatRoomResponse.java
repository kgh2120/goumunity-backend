package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import java.util.List;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyChatRoomResponse {

    private String title;
    private Long chatRoomId;
    private String imgSrc;
    private Integer currentUserCount;
    private Integer unReadMessageCount;
    private List<ChatRoomHashtagResponse> hashtags;

    public MyChatRoomResponse(ChatRoomEntity chatRoom) {
        this.title = chatRoom.getTitle();
        this.chatRoomId = chatRoom.getId();
        this.imgSrc = chatRoom.getImgSrc();
        this.currentUserCount = chatRoom.getUserChatRooms().size();
        this.unReadMessageCount = 0;
        this.hashtags =
                chatRoom.getChatRoomHashtags().stream().map(ChatRoomHashtagResponse::from).toList();
    }
}
