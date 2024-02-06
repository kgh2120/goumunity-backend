package com.ssafy.goumunity.domain.user.service.port;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageUploader {

    /**
     * profileImage를 전달하면, 주소를 리턴합니다., profileImage가 null이라면 null을 리턴합니다.
     *
     * @param profileImage
     * @return
     */
    String uploadProfileImage(MultipartFile profileImage);

    boolean isExistsImgSrc(String imgSrc);
}
