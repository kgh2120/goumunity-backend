package com.ssafy.goumunity.util;

import com.ssafy.goumunity.image.Image;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MultiImageHandler {
    public List<Image> parseFileInfo(Long writerId, List<MultipartFile> multipartFiles) throws IOException {
        List<Image> fileList = new ArrayList<>();

        if(multipartFiles == null || multipartFiles.isEmpty()) return fileList;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        String absolutePath = new File("").getAbsolutePath() + "\\";


        // 저장 path 설정 --> 추후 게시판 기능 구현시 활용
        String path = "src/main/resources/images/board/" + current_date;
        String simplePath = "/images/board/" + current_date;
        File file = new File(path);

        if(!file.exists()){
            file.mkdirs();
        }

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){
                String contentType = multipartFile.getContentType();
                String originalFileExtension;

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
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
                        break;
                    }
                }

                String new_file_name = Long.toString(System.nanoTime()) + originalFileExtension;
                Image img = Image.builder()
                        .originalFileName(multipartFile.getOriginalFilename())
                        .storedFilePath(simplePath + "/" + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();
                fileList.add(img);

                file = new File(absolutePath + path + "/" + new_file_name);
                multipartFile.transferTo(file);
            }
        }

        return fileList;
    }
}
