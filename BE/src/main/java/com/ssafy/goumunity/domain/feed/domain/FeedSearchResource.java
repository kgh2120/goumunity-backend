package com.ssafy.goumunity.domain.feed.domain;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.goumunity.domain.feed.controller.response.FeedImgResponse;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedSavingCategory;
import com.ssafy.goumunity.domain.region.controller.response.RegionResponse;
import com.ssafy.goumunity.domain.user.controller.response.UserResponse;
import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedSearchResource {
    private Long feedId;
    private String content;
    private FeedCategory feedCategory;
    private FeedSavingCategory savingCategory;
    private Integer price;
    private Integer afterPrice;

    private RegionResponse region;
    private UserResponse user;

    private Long createdAt;
    private Long updateAt;

    private List<FeedImgResponse> images;
    private Long commentCount;
    private Long likeCount;

    private Boolean iLikeThat;
    private Boolean isScrapped;

    @QueryProjection
    public FeedSearchResource(
            FeedEntity feed, Long commentCount, Long likeCount, Boolean iLikeThat, Boolean isScrapped) {
        this.feedId = feed.getId();
        this.content = feed.getContent();
        this.feedCategory = feed.getFeedCategory();
        this.savingCategory = feed.getSavingCategory();
        this.price = feed.getPrice();
        this.afterPrice = feed.getAfterPrice();
        this.region = RegionResponse.from(feed.getRegionEntity().to());
        this.user = UserResponse.from(feed.getUserEntity().toModel());
        this.createdAt = feed.getCreatedAt().toEpochMilli();
        this.updateAt = feed.getUpdatedAt().toEpochMilli();
        this.images = feed.getImages().stream().map(FeedImgResponse::from).toList();
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.iLikeThat = iLikeThat;
        this.isScrapped = isScrapped;
    }
}
