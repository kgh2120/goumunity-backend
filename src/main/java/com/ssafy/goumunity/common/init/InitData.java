package com.ssafy.goumunity.common.init;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.domain.Hashtag;
import com.ssafy.goumunity.domain.chat.infra.chatroom.ChatRoomEntity;
import com.ssafy.goumunity.domain.chat.infra.chatroom.UserChatRoomEntity;
import com.ssafy.goumunity.domain.chat.infra.hashtag.ChatRoomHashtagEntity;
import com.ssafy.goumunity.domain.chat.infra.hashtag.HashtagEntity;
import com.ssafy.goumunity.domain.feed.infra.comment.CommentEntity;
import com.ssafy.goumunity.domain.feed.infra.commentlike.CommentLikeEntity;
import com.ssafy.goumunity.domain.feed.infra.feed.FeedEntity;
import com.ssafy.goumunity.domain.feed.infra.feedimg.FeedImgEntity;
import com.ssafy.goumunity.domain.feed.infra.feedlike.FeedLikeEntity;
import com.ssafy.goumunity.domain.feed.infra.feedscrap.FeedScrapEntity;
import com.ssafy.goumunity.domain.feed.infra.reply.ReplyEntity;
import com.ssafy.goumunity.domain.feed.infra.replylike.ReplyLikeEntity;
import com.ssafy.goumunity.domain.region.controller.request.RegionRequest;
import com.ssafy.goumunity.domain.region.domain.Region;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.controller.request.UserRequest;
import com.ssafy.goumunity.domain.user.domain.Gender;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserCategory;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Profile({"local"})
@RequiredArgsConstructor
@Component
public class InitData implements InitializingBean {

    private final EntityManagerFactory emf;
    private final PasswordEncoder encoder;

    private String[] regionNames = {
        "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구",
        "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
    };

    @Override
    public void afterPropertiesSet() throws Exception {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        RegionEntity region = null;
        for (String regionName : regionNames) {
            region =
                    RegionEntity.from(
                            Region.from(RegionRequest.builder().si("서울").gungu(regionName).build()));
            em.persist(region);
        }
        log.info("save region!");

        String encode = encoder.encode("1q2w3e4rQ!");
        UserRequest.Create ur1 =
                UserRequest.Create.builder()
                        .email("t1@naver.com")
                        .password(encode)
                        .nickname("역삼 하우스푸어 대현 1414")
                        .age(25)
                        .gender(Gender.MALE)
                        .monthBudget(5_0000L)
                        .userCategory(UserCategory.COLLEGE_STUDENT)
                        .regionId(region.getRegionId())
                        .build();

        UserEntity user =
                UserEntity.fromModel(
                        User.create(
                                ur1,
                                "https://goumunity.s3.ap-northeast-2.amazonaws.com/static/%EA%B9%9D%EC%B9%98%EC%A7%80%EB%A7%88.jpg",
                                encode));
        em.persist(user);

        UserRequest.Create ur2 =
                UserRequest.Create.builder()
                        .email("t2@naver.com")
                        .password(encode)
                        .nickname("낙성대 왕초 규준 4525")
                        .age(27)
                        .gender(Gender.MALE)
                        .monthBudget(40_0000L)
                        .regionId(region.getRegionId())
                        .userCategory(UserCategory.EMPLOYEE)
                        .build();

        UserEntity user2 =
                UserEntity.fromModel(
                        User.create(
                                ur2,
                                "https://goumunity.s3.ap-northeast-2.amazonaws.com/static/%EA%B9%9D%EC%B9%98%EC%A7%80%EB%A7%88.jpg",
                                encode));
        em.persist(user2);

        ur2 =
                UserRequest.Create.builder()
                        .email("t3@naver.com")
                        .password(encode)
                        .nickname("잠실 프린세스 예은 5599")
                        .age(28)
                        .gender(Gender.FEMALE)
                        .monthBudget(100_0000L)
                        .regionId(region.getRegionId())
                        .userCategory(UserCategory.JOB_SEEKER)
                        .build();

        UserEntity user3 =
                UserEntity.fromModel(
                        User.create(
                                ur2,
                                "https://goumunity.s3.ap-northeast-2.amazonaws.com/static/%EA%B9%9D%EC%B9%98%EC%A7%80%EB%A7%88.jpg",
                                encode));
        em.persist(user3);

        HashtagEntity h1 = HashtagEntity.from(Hashtag.create("20대"));
        em.persist(h1);
        HashtagEntity h2 = HashtagEntity.from(Hashtag.create("관악구"));
        em.persist(h2);
        HashtagEntity seochoHashtag = HashtagEntity.from(Hashtag.create("서초구"));
        em.persist(seochoHashtag);
        HashtagEntity jobSeekerHashtag = HashtagEntity.from(Hashtag.create("구직자"));
        em.persist(jobSeekerHashtag);
        HashtagEntity poorHashtag = HashtagEntity.from(Hashtag.create("거지"));
        em.persist(poorHashtag);
        HashtagEntity monthBudget = HashtagEntity.from(Hashtag.create("월 평균 10만원"));
        em.persist(monthBudget);
        HashtagEntity nakseungdae = HashtagEntity.from(Hashtag.create("낙성대"));
        em.persist(nakseungdae);
        log.info("save hashtag!");
        ChatRoomEntity chatRoom =
                ChatRoomEntity.from(
                        ChatRoom.create(
                                ChatRoomRequest.Create.builder()
                                        .title("하우스푸어들만 오세요")
                                        .capability(10)
                                        .regionId(region.getRegionId())
                                        .build(),
                                region.getRegionId(),
                                user.getId(),
                                "https://goumunity.s3.ap-northeast-2.amazonaws.com/static/%EC%84%9C%EC%B4%88%EA%B5%AC.jpg",
                                List.of(1L, 2L, 3L)));

        em.persist(chatRoom);

        em.persist(ChatRoomHashtagEntity.create(h1, chatRoom, 1));

        em.persist(ChatRoomHashtagEntity.create(seochoHashtag, chatRoom, 2));

        em.persist(ChatRoomHashtagEntity.create(monthBudget, chatRoom, 3));

        em.persist(UserChatRoomEntity.create(user, chatRoom));
        em.persist(UserChatRoomEntity.create(user3, chatRoom));
        log.info("save chatRoom!");
        List<UserEntity> testingUser = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserRequest.Create testUserDomain =
                    UserRequest.Create.builder()
                            .email("t3@naver.com")
                            .password(encode)
                            .nickname("잠실 프린세스 예은 5599")
                            .age(28)
                            .gender(Gender.FEMALE)
                            .monthBudget(100_0000L)
                            .regionId(region.getRegionId())
                            .userCategory(UserCategory.JOB_SEEKER)
                            .build();

            UserEntity testUserEntity =
                    UserEntity.fromModel(
                            User.create(
                                    testUserDomain,
                                    "https://goumunity.s3.ap-northeast-2.amazonaws.com/static/%EA%B9%9D%EC%B9%98%EC%A7%80%EB%A7%88.jpg",
                                    encode));
            em.persist(testUserEntity);
            testingUser.add(testUserEntity);
        }
        log.info("save additional user!");

        for (int i = 0; i < 1; i++) {
            UserRequest.Create uc =
                    UserRequest.Create.builder()
                            .email("p"+i + "@naver.com")
                            .password(encode)
                            .nickname("역삼 하우스푸어 대혀니" + i)
                            .age(25)
                            .gender(Gender.MALE)
                            .monthBudget(5_0000L)
                            .userCategory(UserCategory.COLLEGE_STUDENT)
                            .regionId(region.getRegionId())
                            .build();

            UserEntity uu =
                    UserEntity.fromModel(
                            User.create(
                                    uc,
                                    "https://goumunity.s3.ap-northeast-2.amazonaws.com/static/%EA%B9%9D%EC%B9%98%EC%A7%80%EB%A7%88.jpg",
                                    encode));
            em.persist(uu);

            for (int a = 0; a < 10; a++) {
                FeedEntity feed = FeedEntity.builder().content("1234").userEntity(uu).build();

                em.persist(feed);

                for (int j = 0; j < 5; j++) {
                    em.persist(FeedImgEntity.builder().sequence(j).feedEntity(feed).build());
                }

                for (UserEntity testUser : testingUser) {
                    em.persist(FeedLikeEntity.builder().feedEntity(feed).userEntity(testUser).build());
                    em.persist(FeedScrapEntity.builder().feedEntity(feed).userEntity(testUser).build());
                }
                for (int j = 0; j < 100; j++) {
                    CommentEntity comment =
                            CommentEntity.builder().userEntity(user).feedEntity(feed).build();

                    em.persist(comment);

                    for (UserEntity testUser : testingUser) {
                        em.persist(

                                CommentLikeEntity.builder().commentEntity(comment).userEntity(testUser).build());
                    }

                    for (int k = 0; k < 100; k++) {
                        ReplyEntity reply =
                                ReplyEntity.builder().commentEntity(comment).userEntity(user).build();
                        em.persist(reply);

//                        for (int l = 0; l < 5; l++) {
//                            em.persist(ReplyLikeEntity.builder().replyEntity(reply).userEntity(testingUser.get(l)).build());
//                        }
                        for (UserEntity testUser : testingUser) {
                            em.persist(ReplyLikeEntity.builder().replyEntity(reply).userEntity(testUser).build());
                        }
                    }
                }
                log.info("feed{} saved!", a);
            }

        }




        tx.commit();
        log.info("commit!");
    }
}
