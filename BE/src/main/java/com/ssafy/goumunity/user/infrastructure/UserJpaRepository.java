package com.ssafy.goumunity.user.infrastructure;

import com.ssafy.goumunity.user.domain.UserStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailAndUserStatus(String email, UserStatus userStatus);
}
