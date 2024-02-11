package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import com.ssafy.goumunity.domain.feed.domain.FeedRecommendResource;
import com.ssafy.goumunity.domain.feed.domain.FeedSavingCategory;
import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedRecommend {
    private Long feedId;
    private String content;
    private FeedCategory feedCategory;
    private FeedSavingCategory savingCategory;
    private Integer price;
    private Integer afterPrice;

    private Long regionId;
    private String si;
    private String gungu;

    private String nickname;
    private String email;
    private String imgSrc;

    private Long createdAt;
    private Long updatedAt;

    private List<FeedImgResponse> images;
    private Long commentCount;
    private Long likeCount;

    private Boolean iLikeThat;

    public static FeedRecommend from(FeedRecommendResource feedRecommendResource) {
        return FeedRecommend.builder()
                .feedId(feedRecommendResource.getFeedId())
                .content(feedRecommendResource.getContent())
                .feedCategory(feedRecommendResource.getFeedCategory())
                .savingCategory(feedRecommendResource.getSavingCategory())
                .price(feedRecommendResource.getPrice())
                .afterPrice(feedRecommendResource.getAfterPrice())
                .regionId(feedRecommendResource.getRegion().getRegionId())
                .si(feedRecommendResource.getRegion().getSi())
                .gungu(feedRecommendResource.getRegion().getGungu())
                .nickname(feedRecommendResource.getUser().getNickname())
                .email(feedRecommendResource.getUser().getEmail())
                .imgSrc(feedRecommendResource.getUser().getImgSrc())
                .createdAt(feedRecommendResource.getCreatedAt())
                .updatedAt(feedRecommendResource.getUpdatedAt())
                .images(feedRecommendResource.getImages())
                .commentCount(feedRecommendResource.getCommentCount())
                .likeCount(feedRecommendResource.getLikeCount())
                .iLikeThat(feedRecommendResource.getILikeThat())
                .build();
    }
}
