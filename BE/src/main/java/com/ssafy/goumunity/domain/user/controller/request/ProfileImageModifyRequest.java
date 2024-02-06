package com.ssafy.goumunity.domain.user.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class ProfileImageModifyRequest {

    @NotBlank String imgSrc;
}
