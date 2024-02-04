package com.ssafy.goumunity.common.controller;

import com.ssafy.goumunity.common.controller.response.ImageUploadResponse;
import com.ssafy.goumunity.common.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/common")
public class CommonController {
    private final FileUploadService fileUploadService;

    @PostMapping("/images")
    public ResponseEntity<ImageUploadResponse> uploadImageFile(
            @RequestPart("image") MultipartFile multipartFile) {
        return ResponseEntity.ok(ImageUploadResponse.from(fileUploadService.uploadFile(multipartFile)));
    }
}
