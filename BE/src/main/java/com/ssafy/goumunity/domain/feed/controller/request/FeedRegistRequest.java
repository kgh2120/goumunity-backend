package com.ssafy.goumunity.domain.feed.controller.request;

import com.ssafy.goumunity.domain.feed.domain.FeedCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedRegistRequest {
    @NotBlank(message = "내용이 있어야 합니다.")
    private String content;

    @NotNull(message = "카테고리를 선택해야 합니다.")
    private FeedCategory feedCategory;

    private Integer price;
    private Integer afterPrice;
    private Integer profit;

    @NotNull(message = "지역을 선택해야 합니다.")
    private Long regionId;

    @NotNull(message = "작성자 정보가 없습니다.")
    private Long userId;
}
