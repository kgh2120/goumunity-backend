package com.ssafy.goumunity.common.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.amazonaws.services.s3.AmazonS3Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class FileUploadServiceImplTest {

    @MockBean AmazonS3Client amazonS3Client;

    @InjectMocks FileUploadServiceImpl fileUploadService;

    @Test
    void fileEmpty테스트_MultiPartFile이_NULL인경우() throws Exception {
        // given
        MockMultipartFile file = null;
        // when
        String sut = fileUploadService.uploadFile(file);
        // then
        assertThat(sut).isNull();
    }

    @Test
    void fileEmpty테스트_MultiPartFile이_Empty인경우() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("image", new byte[0]);
        // when
        String sut = fileUploadService.uploadFile(file);
        // then
        assertThat(sut).isNull();
    }
}
