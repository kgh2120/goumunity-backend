package com.ssafy.goumunity.domain.feed.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeCountRequest;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentLikeServiceTest {

    @Mock CommentLikeRepository commentLikeRepository;

    @InjectMocks CommentLikeServiceImpl commentLikeService;

    @Nested
    class 좋아요기능확인 {

        @Test
        @DisplayName("좋아요_갯수확인_성공")
        void 좋아요개수확인테스트() {

            CommentLikeCountRequest commentLikeCountRequest =
                    CommentLikeCountRequest.builder().commentId(Long.valueOf(1)).build();

            BDDMockito.given(commentLikeRepository.countCommentLikeByCommentId(any())).willReturn(19);

            assertEquals(
                    commentLikeService.countCommentLikeByCommentId(commentLikeCountRequest).getLikeCount(),
                    19);
        }

        @Test
        @DisplayName("좋아요추가_성공")
        void 좋아요추가하기() {
            assertDoesNotThrow(() -> commentLikeService.likeButton(Long.valueOf(1), Long.valueOf(1)));
        }

        @Test
        @DisplayName("좋아요제거_성공")
        void 좋아요제거하기() {
            assertDoesNotThrow(() -> commentLikeService.unLikeButton(Long.valueOf(1), Long.valueOf(1)));
        }
    }
}
