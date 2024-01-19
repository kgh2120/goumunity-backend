package com.ssafy.goumunity.user.service.port;

import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.domain.UserStatus;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    User modify(User user);

    void delete(User user);
}
