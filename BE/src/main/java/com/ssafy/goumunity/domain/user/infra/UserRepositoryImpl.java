package com.ssafy.goumunity.domain.user.infra;

import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserStatus;
import com.ssafy.goumunity.domain.user.service.port.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User create(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJpaRepository.findByEmailAndUserStatus(email, userStatus).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByIdAndUserStatus(Long userId, UserStatus userStatus) {
        return userJpaRepository.findByIdAndUserStatus(userId, userStatus).map(UserEntity::toModel);
    }

    @Override
    public List<UserRankingInterface> findUserRanking() {
        return userJpaRepository.findUserRanking();
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmailAndUserStatus(String email, UserStatus userStatus) {
        return userJpaRepository.existsByEmailAndUserStatus(email, userStatus);
    }

    @Override
    public User modify(User user) {
        return userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }

    @Override
    public void delete(User user) {
        userJpaRepository.save(UserEntity.fromModel(user)).toModel();
    }
}
