package com.ssafy.goumunity.domain.user.controller.request;

import com.ssafy.goumunity.domain.user.domain.UserCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserModifyRequest {

    private Long monthBudget;

    @Min(message = "나이는 1살 이상이여야 합니다.", value = 0)
    @Max(message = "나이는 150살 이하여야 합니다.", value = 150)
    private Integer age;

    private UserCategory userCategory;

    private String nickname;

    private Long regionId;
}
