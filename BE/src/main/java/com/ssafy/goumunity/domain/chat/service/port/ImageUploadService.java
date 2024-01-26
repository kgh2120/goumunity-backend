package com.ssafy.goumunity.domain.chat.service.port;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    String uploadImage(MultipartFile imageFile);
}
