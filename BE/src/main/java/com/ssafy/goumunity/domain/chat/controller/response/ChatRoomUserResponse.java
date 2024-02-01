package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.user.infra.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
