package com.ssafy.goumunity.domain.chat.infra.chatroom;

import com.ssafy.goumunity.common.service.FileUploadService;
import com.ssafy.goumunity.domain.chat.service.port.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class ImageUploadServiceImpl implements ImageUploadService {

    private final FileUploadService fileUploadService;

    @Override
    public String uploadImage(MultipartFile imageFile) {
        return fileUploadService.uploadFile(imageFile);
    }
}
