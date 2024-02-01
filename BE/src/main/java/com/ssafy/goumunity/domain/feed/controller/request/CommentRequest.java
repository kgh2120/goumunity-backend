package com.ssafy.goumunity.domain.feed.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class CommentRequest {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Create {
        @NotBlank
        @Size(min = 1, max = 200, message = "적절한 글자수로 입력해주세요.")
        private String content;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Getter
    public static class Modify {
        @NotBlank
        @Size(min = 1, max = 200, message = "적절한 글자수로 입력해주세요.")
        private String content;
    }
}
