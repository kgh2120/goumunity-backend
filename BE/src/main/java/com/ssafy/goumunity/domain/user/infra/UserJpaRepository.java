package com.ssafy.goumunity.domain.user.infra;

import com.ssafy.goumunity.domain.user.domain.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndUserStatus(String email, UserStatus userStatus);

    boolean existsByNickname(String nickname);

    boolean existsByEmailAndUserStatus(String email, UserStatus userStatus);
}
