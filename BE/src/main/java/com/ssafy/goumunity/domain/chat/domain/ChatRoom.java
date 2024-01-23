package com.ssafy.goumunity.domain.chat.domain;

import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.user.domain.User;
import java.time.Instant;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class ChatRoom {
    private Long id;
    private Boolean isOfficial;
    private String title;
    private String hashtag;
    private Integer capability;
    private Integer currentUser;
    private String imgSrc;

    private Instant createdAt;
    private Instant updatedAt;

    private Region region;
    private User host;
}
