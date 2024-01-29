package com.ssafy.goumunity.domain.feed.service.post;

import org.springframework.web.multipart.MultipartFile;

public interface FeedImageUploader {
    String uploadFeedImage(MultipartFile feedImage);
}
