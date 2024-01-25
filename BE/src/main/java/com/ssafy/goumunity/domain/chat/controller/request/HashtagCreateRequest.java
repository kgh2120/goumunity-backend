package com.ssafy.goumunity.domain.chat.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashtagCreateRequest {

    @Size(max = 10)
    @NotBlank
    @NotNull
    private String name;
}
