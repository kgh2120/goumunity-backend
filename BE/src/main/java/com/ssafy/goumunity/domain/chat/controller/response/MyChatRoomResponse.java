package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.infra.ChatRoomEntity;
import java.util.List;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    public static MyChatRoomResponse from(ChatRoomEntity chatRoomEntity) {
        return MyChatRoomResponse.builder()
                .title(chatRoomEntity.getTitle())
                .chatRoomId(chatRoomEntity.getId())
                .imgSrc(chatRoomEntity.getImgSrc())
                .currentUserCount(chatRoomEntity.getUserChatRooms().size())
                .unReadMessageCount(0)
                .hashtags(
                        chatRoomEntity.getChatRoomHashtags().stream()
                                .map(ChatRoomHashtagResponse::from)
                                .toList())
                .build();
    }
}
