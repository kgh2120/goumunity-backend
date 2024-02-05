package com.ssafy.goumunity.common.controller.response;

import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ImageUploadResponse {

    private String imageSource;

    public static ImageUploadResponse from(String url) {
        return ImageUploadResponse.builder().imageSource(url).build();
    }
}
