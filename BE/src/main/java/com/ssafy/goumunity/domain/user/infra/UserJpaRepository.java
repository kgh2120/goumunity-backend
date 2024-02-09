package com.ssafy.goumunity.domain.user.infra;

import com.ssafy.goumunity.domain.user.domain.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    @Query(
            "select u from UserEntity u join fetch u.regionEntity where u.email = :email and u.userStatus = :userStatus")
    Optional<UserEntity> findByEmailAndUserStatus(String email, UserStatus userStatus);

    boolean existsByNickname(String nickname);

    boolean existsByEmailAndUserStatus(String email, UserStatus userStatus);
}
