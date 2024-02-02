package com.ssafy.goumunity.domain.feed.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.user.controller.response.UserResponse;
import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedRecommendResource {
    private Long feedId;
    private String content;
    private FeedCategory feedCategory;
    private Integer price;
    private Integer afterPrice;

    private RegionResponse region;
    private UserResponse user;

    private Long createdAt;
    private Long updatedAt;

    private List<FeedImgResponse> images;
    private Long commentCount;
    private Long likeCount;

    private Boolean iLikeThat;

    @QueryProjection
    public FeedRecommendResource(
            FeedEntity feed, Long commentCount, Long likeCount, Boolean iLikeThat) {
        this.feedId = feed.getId();
        this.content = feed.getContent();
        this.feedCategory = feed.getFeedCategory();
        this.price = feed.getPrice();
        this.afterPrice = feed.getAfterPrice();
        this.region = RegionResponse.from(feed.getRegionEntity().to());
        this.user = UserResponse.from(feed.getUserEntity().toModel());
        this.createdAt = feed.getCreatedAt().toEpochMilli();
        this.updatedAt = feed.getUpdatedAt().toEpochMilli();
        this.images = feed.getImages().stream().map(FeedImgResponse::from).toList();
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.iLikeThat = iLikeThat;
    }
}
