package com.ssafy.goumunity.user.infrastructure;

import com.ssafy.goumunity.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndUserStatus(String email, UserStatus userStatus);
}
