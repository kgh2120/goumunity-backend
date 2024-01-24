package com.ssafy.goumunity.common.service;

import static com.ssafy.goumunity.common.exception.CustomErrorCode.FILE_IS_NOT_IMAGE_TYPE;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.common.exception.InternalServerCaughtException;
import com.ssafy.goumunity.common.exception.InternalServerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final String IMAGE_CONTENT_TYPE_PREFIX = "image";
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadFile(MultipartFile multipartFile) {

        log.info("file : {}", multipartFile);
        if (multipartFile.isEmpty()) {
            return null;
        }
        validateIsClientSendImageFile(multipartFile);
        return uploadFiles(multipartFile);
    }

    private void validateIsClientSendImageFile(MultipartFile multipartFile) {
        if (!Objects.requireNonNull(multipartFile.getContentType()).contains(IMAGE_CONTENT_TYPE_PREFIX))
            throw new CustomException(FILE_IS_NOT_IMAGE_TYPE);
    }

    private String uploadFiles(MultipartFile multipartFile) {
        File uploadFile =
                convert(multipartFile) // 파일 변환할 수 없으면 에러
                        .orElseThrow(() -> new InternalServerException("MultipartFile -> File 변환 실패", this));
        // TODO Exception 추가
        return upload(uploadFile);
    }

    private String upload(File uploadFile) {
        String fileName = "upload/" + UUID.randomUUID() + uploadFile.getName(); // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            return;
        }
        log.debug("Local File Delete Failed!! ");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        try {
            if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
                try (FileOutputStream fos =
                        new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            throw new InternalServerCaughtException(e, this);
        }
        return Optional.empty();
    }
}
