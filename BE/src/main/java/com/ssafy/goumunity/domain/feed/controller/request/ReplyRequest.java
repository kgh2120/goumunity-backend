package com.ssafy.goumunity.domain.feed.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReplyRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class Create {
        @NotBlank
        @Size(min = 1, max = 200, message = "적절한 글자수로 입력해주세요.")
        private String content;
    }
}
