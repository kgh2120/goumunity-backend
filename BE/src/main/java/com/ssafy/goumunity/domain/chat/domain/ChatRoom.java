package com.ssafy.goumunity.domain.chat.domain;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import java.time.Instant;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ChatRoom {
    private Long id;
    private Boolean isOfficial;
    private String title;

    private Integer capability;
    private Integer currentUser;
    private String imgSrc;
    private Instant createdAt;
    private Instant updatedAt;

    private Long regionId;
    private Long userId;

    private List<Long> hashtagsIds;

    public static ChatRoom create(
            ChatRoomRequest.Create dto,
            Long regionId,
            Long hostId,
            String imgSrc,
            List<Long> hashtagIds) {
        return ChatRoom.builder()
                .isOfficial(false)
                .title(dto.getTitle())
                .hashtagsIds(hashtagIds)
                .capability(dto.getCapability())
                .currentUser(1)
                .imgSrc(imgSrc)
                .createdAt(Instant.now())
                .regionId(regionId)
                .userId(hostId)
                .build();
    }

    public boolean canConnect() {
        return capability > currentUser;
    }
}
