package com.ssafy.goumunity.image;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Image {
    private String originalFileName;
    private String storedFilePath;
    private Long fileSize;
}
