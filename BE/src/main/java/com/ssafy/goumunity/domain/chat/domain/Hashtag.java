package com.ssafy.goumunity.domain.chat.domain;

import com.ssafy.goumunity.domain.chat.controller.request.HashtagCreateRequest;
import java.time.Instant;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Hashtag {
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;

    public static Hashtag create(HashtagCreateRequest dto) {
        return Hashtag.builder().name(dto.getName()).createdAt(Instant.now()).build();
    }
}