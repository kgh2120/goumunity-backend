package com.ssafy.goumunity.domain.feed.service;

import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeCountRequest;
import com.ssafy.goumunity.domain.feed.controller.request.CommentLikeRequest;
import com.ssafy.goumunity.domain.feed.domain.CommentLike;
import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;
import com.ssafy.goumunity.domain.feed.service.post.CommentLikeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CommentLikeServiceTest {

    @Mock
    CommentLikeRepository commentLikeRepository;

    @InjectMocks
    CommentLikeServiceImpl commentLikeService;

    @Nested
    class 좋아요기능확인{

        @Test
        @DisplayName("좋아요_갯수확인_성공")
        void 좋아요개수확인테스트(){

            CommentLikeCountRequest commentLikeCountRequest = CommentLikeCountRequest.builder()
                    .commentId(Long.valueOf(1))
                    .build();

            BDDMockito.given(
                    commentLikeRepository.countCommentLikeByCommentId(any())
            ).willReturn(19);

            assertEquals(commentLikeService.countCommentLikeByCommentId(commentLikeCountRequest).getLikeCount(), 19);

        }

        @Test
        @DisplayName("좋아요추가_성공")
        void 좋아요추가하기(){

            CommentLikeRequest commentLikeRequest = CommentLikeRequest.builder()
                    .commentId(Long.valueOf(1))
                    .userId(Long.valueOf(1))
                    .build();

            BDDMockito.given(
                    commentLikeRepository.findOneByCommentIdAndUserId(any(), any())
            ).willReturn(Optional.empty());

            assertTrue(commentLikeService.pushLikeButton(commentLikeRequest, Long.valueOf(1)));

        }

        @Test
        @DisplayName("좋아요제거_성공")
        void 좋아요제거하기(){

            CommentLikeRequest commentLikeRequest = CommentLikeRequest.builder()
                    .commentId(Long.valueOf(1))
                    .userId(Long.valueOf(1))
                    .build();

            BDDMockito.given(
                    commentLikeRepository.findOneByCommentIdAndUserId(any(), any())
            ).willReturn(Optional.of(CommentLike.builder().build()));

            assertFalse(commentLikeService.pushLikeButton(commentLikeRequest, Long.valueOf(1)));

        }

    }

}
