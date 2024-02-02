package com.ssafy.goumunity.domain.feed.domain;

import com.ssafy.goumunity.domain.feed.controller.request.FeedRequest;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.time.Instant;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"})
public class Feed {
    private Long id;
    private String content;
    private FeedCategory feedCategory;
    private Integer price;
    private Integer afterPrice;
    private Long regionId;
    private Long userId;
    private Instant createdAt;
    private Instant updatedAt;

    public void checkUser(Long userId) {
        if (!this.getUserId().equals(userId)) throw new UserException(UserErrorCode.INVALID_USER);
    }

    public static Feed create(FeedRequest.Create feedRequest, Long userId) {
        return Feed.builder()
                .content(feedRequest.getContent())
                .feedCategory(feedRequest.getFeedCategory())
                .price(feedRequest.getPrice())
                .afterPrice(feedRequest.getAfterPrice())
                .regionId(feedRequest.getRegionId())
                .userId(userId)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public static Feed create(Feed feed, FeedRequest.Modify feedRequest) {
        return Feed.builder()
                .id(feed.getId())
                .content(feedRequest.getContent())
                .feedCategory(feedRequest.getFeedCategory())
                .price(feedRequest.getPrice())
                .afterPrice(feedRequest.getAfterPrice())
                .regionId(feedRequest.getRegionId())
                .userId(feed.userId)
                .createdAt(feed.createdAt)
                .updatedAt(Instant.now())
                .build();
    }
}
