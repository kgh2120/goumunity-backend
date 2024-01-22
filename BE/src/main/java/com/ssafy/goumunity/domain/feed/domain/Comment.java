package com.ssafy.goumunity.domain.feed.domain;

import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    private Long commentId;
    private String content;
    private String image;

    private Instant createdAt;
    private Instant updatedAt;
}
