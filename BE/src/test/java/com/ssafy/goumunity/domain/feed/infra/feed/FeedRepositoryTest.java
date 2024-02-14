package com.ssafy.goumunity.domain.feed.infra.feed;

import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FeedRepositoryTest {

    @Autowired FeedJpaRepository feedJpaRepository;

    @Autowired EntityManager em;

    @Test
    void 유저_아이디로_피드_삭제_테스트_성공() throws Exception {
        // given
        UserEntity user = UserEntity.builder().build();
        em.persist(user);
        for (int i = 0; i < 10; i++) {
            feedJpaRepository.save(FeedEntity.builder().userEntity(user).build());
        }
        List<Long> sut = feedJpaRepository.findAllFeedIdsByUserId(user.getId());

        //        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        //
        // jpaQueryFactory.delete(feedEntity).where(feedEntity.userEntity.id.eq(user.getId())).execute();
        //        // when
        //        List<FeedEntity> sut = feedJpaRepository.findAll();
        //        // then
        //        assertThat(sut).isEmpty();
    }
}
