package com.ssafy.goumunity.domain.user.service.port;

import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserStatus;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    User modify(User user);

    void delete(User user);
}
