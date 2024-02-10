package com.ssafy.goumunity.domain.chat.domain;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.user.domain.User;
import java.time.Instant;
import java.util.List;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class ChatRoom {
    private Long id;
    private Boolean isOfficial;
    private String title;

    private Integer capability;
    private Integer currentUserCount;
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
                .currentUserCount(1)
                .imgSrc(imgSrc)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .regionId(regionId)
                .userId(hostId)
                .build();
    }

    public boolean canConnect() {
        return capability > currentUserCount;
    }

    public boolean isHost(User user) {
        return userId.equals(user.getId());
    }

    public boolean isHost(Long userId) {
        return this.userId.equals(userId);
    }

    public boolean isHostAlone() {
        return currentUserCount.equals(1);
    }

    public void modify(ChatRoomRequest.Modify dto, List<Long> hashtagsIds, String imageSource) {
        title = dto.getTitle();
        capability = dto.getCapability();
        imgSrc = imageSource;
        updatedAt = Instant.now();
        this.hashtagsIds = hashtagsIds;
    }

    public void modifyHost(Long userId) {
        this.userId = userId;
    }
}
