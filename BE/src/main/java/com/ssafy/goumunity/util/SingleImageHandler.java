package com.ssafy.goumunity.util;

import com.ssafy.goumunity.image.Image;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SingleImageHandler {
    public Image parseFileInfo(MultipartFile image) {
        if(image == null) return null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        String absolutePath = new File("").getAbsolutePath() + "\\";

        String path = "src/main/resources/images/user-profile/" + current_date;
        String simplePath = "/images/user-profile/" + current_date;
        File file = new File(path);

        if(!file.exists()){
            file.mkdirs();
        }

        String contentType = image.getContentType();
        String originalFileExtension;

        if (ObjectUtils.isEmpty(contentType)) {
            return null;
        }
        else {
            if(contentType.contains("image/jpeg")){
                originalFileExtension = ".jpg";
            }
            else if(contentType.contains("image/png")){
                originalFileExtension = ".png";
            }
            else if(contentType.contains("image/gif")){
                originalFileExtension = ".gif";
            }
            else {
                // TODO: 지원하는 파일 확장자가 아닐 경우 에러 처리
                return null;
            }
        }

        String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
        Image img = Image.builder()
                .originalFileName(image.getOriginalFilename())
                .storedFilePath(simplePath + "/" + new_file_name)
                .fileSize(image.getSize())
                .build();

        file = new File(absolutePath + path + "/" + new_file_name);

        try {
            image.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return img;
    }
}
