package com.ssafy.goumunity.domain.feed.controller.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FeedImgRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Modify {

        @Min(value = 1)
        private Integer sequence;

        @NotBlank private String imgSrc;
    }
}
