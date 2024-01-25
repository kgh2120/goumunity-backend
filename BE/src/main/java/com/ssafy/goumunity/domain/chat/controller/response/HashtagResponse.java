package com.ssafy.goumunity.domain.chat.controller.response;

import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashtagResponse {
    private Long id;
    private String name;

    public static HashtagResponse from(Hashtag hashtag) {
        return new HashtagResponse(hashtag.getId(), hashtag.getName());
    }
}
