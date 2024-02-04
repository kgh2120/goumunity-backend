package com.ssafy.goumunity.domain.chat.controller.request;

import com.ssafy.goumunity.common.constraint.MaxListSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChatRoomRequest {

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Create {

        @NotNull
        @NotBlank
        @Size(max = 30)
        private String title;

        @Min(1)
        private Integer capability;

        @Min(1)
        private Long regionId;

        @MaxListSize(5)
        private List<HashtagRequest> hashtags;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Modify {
        private String title;
        private Integer capability;
        private Long leader;
        private String image;

        @MaxListSize(5)
        private List<HashtagRequest> hashtagRequests;
    }

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HashtagRequest {
        private Long id;
    }
}
