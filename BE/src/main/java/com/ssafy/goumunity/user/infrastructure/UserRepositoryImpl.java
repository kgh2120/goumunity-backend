package com.ssafy.goumunity.user.infrastructure;

import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.domain.UserStatus;
import com.ssafy.goumunity.user.service.port.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJpaRepository.findByEmailAndUserStatus(email, userStatus).map(UserEntity::toModel);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }
}
