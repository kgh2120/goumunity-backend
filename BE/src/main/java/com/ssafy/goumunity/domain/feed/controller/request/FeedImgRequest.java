package com.ssafy.goumunity.domain.feed.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class FeedImgRequest {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Modify {

        @Min(value = 1)
        private Integer sequence;

        @NotBlank private String imgSrc;
    }
}
