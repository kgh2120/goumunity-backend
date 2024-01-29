package com.ssafy.goumunity.domain.feed.infra.feedimg;

import com.ssafy.goumunity.common.service.FileUploadService;
import com.ssafy.goumunity.domain.feed.service.post.FeedImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class FeedImageUploaderImpl implements FeedImageUploader {

    private final FileUploadService fileUploadService;

    @Override
    public String uploadFeedImage(MultipartFile feedImage) {
        return fileUploadService.uploadFile(feedImage);
    }
}
