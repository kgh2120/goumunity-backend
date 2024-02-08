package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.user.domain.User;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class FeedCreateResponse {
    private Long feedId;
    private Boolean isAuthenticated;
    private User user;

    public static FeedCreateResponse create(Long feedId, Boolean isChanged, User user) {
        return FeedCreateResponse.builder()
                .feedId(feedId)
                .isAuthenticated(isChanged)
                .user(user)
                .build();
    }
}
