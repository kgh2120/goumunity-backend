package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.user.infra.UserEntity;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomUserResponse {

    private Long userId;
    private String nickname;
    private String profileImageSrc;
    private Boolean isCurrentUser;

    public ChatRoomUserResponse(UserEntity user, Long currentUserId) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.profileImageSrc = user.getImgSrc();
        this.isCurrentUser = user.getId().equals(currentUserId);
    }
}
