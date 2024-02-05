package com.ssafy.goumunity.domain.user.infra;

import com.ssafy.goumunity.common.service.FileUploadService;
import com.ssafy.goumunity.domain.user.service.port.ProfileImageUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ProfileImageUploaderImpl implements ProfileImageUploader {

    private final FileUploadService fileUploadService;

    @Override
    public String uploadProfileImage(MultipartFile multipartFile) {
        return fileUploadService.uploadFile(multipartFile, true);
    }
}
