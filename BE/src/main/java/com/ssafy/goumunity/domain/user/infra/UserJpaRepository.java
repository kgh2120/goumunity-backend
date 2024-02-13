package com.ssafy.goumunity.domain.user.infra;

import com.ssafy.goumunity.domain.user.domain.UserStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query(
            "select u from UserEntity u join fetch u.regionEntity where u.email = :email and u.userStatus = :userStatus")
    Optional<UserEntity> findByEmailAndUserStatus(String email, UserStatus userStatus);

    @Query(
            "select u from UserEntity u join fetch u.regionEntity where u.id = :userId and u.userStatus = :userStatus")
    Optional<UserEntity> findByIdAndUserStatus(Long userId, UserStatus userStatus);

    @Query(
            "select u from UserEntity u join fetch u.regionEntity where u.nickname = :nickname and u.userStatus = :userStatus")
    Optional<UserEntity> findByNicknameAndUserStatus(String nickname, UserStatus userStatus);

    @Query(
            value =
                    "SELECT u.user_id as id, u.email, u.nickname, u.img_src as imgSrc, IFNULL(SUM(ff.like_count), 0) AS likedSum "
                            + "FROM users AS u "
                            + "LEFT JOIN "
                            + "(SELECT f.feed_id, f.user_id, COUNT(fl.feed_like_id) AS like_count "
                            + "FROM feed AS f "
                            + "LEFT JOIN feed_like AS fl ON f.feed_id = fl.feed_id "
                            + "GROUP BY f.feed_id) AS ff "
                            + "ON u.user_id = ff.user_id "
                            + "GROUP BY u.user_id "
                            + "ORDER BY likedSum "
                            + "DESC LIMIT 10;",
            nativeQuery = true)
    List<UserRankingInterface> findUserRanking();

    boolean existsByNickname(String nickname);

    boolean existsByEmailAndUserStatus(String email, UserStatus userStatus);
}
