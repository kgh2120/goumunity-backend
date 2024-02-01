package com.ssafy.goumunity.domain.feed.controller.request;

import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

public class FeedRequest {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Create {
        @NotBlank(message = "내용이 있어야 합니다.")
        private String content;

        @NotNull(message = "카테고리를 선택해야 합니다.")
        private FeedCategory feedCategory;

        private Integer price;
        private Integer afterPrice;

        @NotNull(message = "지역을 선택해야 합니다.")
        private Long regionId;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Modify {
        @NotBlank(message = "내용이 있어야 합니다.")
        private String content;

        @NotNull(message = "카테고리를 선택해야 합니다.")
        private FeedCategory feedCategory;

        private Integer price;
        private Integer afterPrice;

        @NotNull(message = "지역을 선택해야 합니다.")
        private Long regionId;

        private List<FeedImgRequest.Modify> feedImages;
    }
}
