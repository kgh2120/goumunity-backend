package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.common.config.cache.CacheEvictMessage;
import com.ssafy.goumunity.common.event.Events;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.controller.request.UserRequest;
import com.ssafy.goumunity.domain.user.controller.response.UserRankingResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserStatus;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import com.ssafy.goumunity.domain.user.service.port.MyChatRoomFindService;
import com.ssafy.goumunity.domain.user.service.port.ProfileImageUploader;
import com.ssafy.goumunity.domain.user.service.port.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final ProfileImageUploader profileImageUploader;
    private final MyChatRoomFindService myChatRoomFindService;

    @Override
    @Transactional
    public Long createUser(UserRequest.Create userCreateRequest, MultipartFile profileImage) {
        // 이메일 중복 검사
        if (userRepository.existsByEmailAndUserStatus(
                userCreateRequest.getEmail(), UserStatus.ACTIVE)) {
            throw new UserException(UserErrorCode.EXIST_EMAIL);
        }

        String imgPath = profileImageUploader.uploadProfileImage(profileImage);
        User user =
                User.create(userCreateRequest, imgPath, encoder.encode(userCreateRequest.getPassword()));
        return userRepository.create(user).getId();
    }
    @Cacheable(cacheNames = "userEmail", cacheManager = "cfCacheManager")
    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new UserException(UserErrorCode.EMAIL_NOT_FOUND));
    }

    @Override
    public User findUserByUserId(Long userId) {
        return userRepository
                .findByIdAndUserStatus(userId, UserStatus.ACTIVE)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    @Cacheable(cacheNames = "userNickname", cacheManager = "cfCacheManager")
    @Override
    public User findUserByNickname(String nickname) {
        return userRepository
                .findByNicknameAndUserStatus(nickname, UserStatus.ACTIVE)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
    }

    @Override
    @Transactional
    public User modifyPassword(User user, String password) {
        user.modifyPassword(encoder.encode(password));
        return userRepository.modify(user);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "userNickname", cacheManager = "cfCacheManager", key = "#user.nickname"),
                    @CacheEvict(cacheNames = "userEmail", cacheManager = "cfCacheManager", key = "#user.email"),
            }
    )
    @Transactional
    public User modifyUser(User user, UserRequest.Modify dto) {
        user.modifyUserInfo(dto);
        user =  userRepository.modify(user);

        Events.raise(new CacheEvictMessage("userNickname", user.getNickname()));
        Events.raise(new CacheEvictMessage("userEmail", user.getEmail()));
        return user;
    }

    @Override
    @Transactional
    public String createProfileImage(MultipartFile profileImage) {
        return profileImageUploader.uploadProfileImage(profileImage);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "userNickname", cacheManager = "cfCacheManager", key = "#user.nickname"),
                    @CacheEvict(cacheNames = "userEmail", cacheManager = "cfCacheManager", key = "#user.email"),
            }
    )
    @Transactional
    public User modifyProfileImage(User user, String imgSrc) {
        if (!profileImageUploader.isExistsImgSrc(imgSrc)) {
            throw new UserException(UserErrorCode.IMAGE_NOT_EXISTS);
        }

        user.modifyProfileImage(imgSrc);
        user = userRepository.modify(user);

        Events.raise(new CacheEvictMessage("userNickname", user.getNickname()));
        Events.raise(new CacheEvictMessage("userEmail", user.getEmail()));
        return user;
    }

    @Override
    public boolean isExistNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
    @Override
    @Transactional
    public void deleteUser(User user) {

        user.deleteUser();
        userRepository.delete(user);
    }


    @Override
    public Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable) {
        return myChatRoomFindService.findMyChatRoom(user, time, pageable);
    }

    @Override
    public List<UserRankingResponse> findUserRanking() {
        return userRepository.findUserRanking().stream().map(UserRankingResponse::from).toList();
    }
}
