package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import java.util.List;
import lombok.*;

@ToString
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MyChatRoomResponse {

    private String title;
    private Long chatRoomId;
    private String imgSrc;
    private Integer currentUserCount;
    private Long unReadMessageCount;
    private List<ChatRoomHashtagResponse> hashtags;

    public MyChatRoomResponse(ChatRoomEntity chatRoom, Long unReadMessageCount) {
        this.title = chatRoom.getTitle();
        this.chatRoomId = chatRoom.getId();
        this.imgSrc = chatRoom.getImgSrc();
        this.currentUserCount = chatRoom.getUserChatRooms().size();
        this.unReadMessageCount = unReadMessageCount;
        this.hashtags =
                chatRoom.getChatRoomHashtags().stream().map(ChatRoomHashtagResponse::from).toList();
    }
}
