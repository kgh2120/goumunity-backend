package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.controller.request.UserCreateRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserModifyRequest;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserStatus;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import com.ssafy.goumunity.domain.user.service.port.MyChatRoomFindService;
import com.ssafy.goumunity.domain.user.service.port.ProfileImageUploader;
import com.ssafy.goumunity.domain.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public User createUser(UserCreateRequest userCreateRequest, MultipartFile profileImage) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new UserException(UserErrorCode.EXIST_EMAIL);
        }

        String imgPath = profileImageUploader.uploadProfileImage(profileImage);
        User user =
                User.from(userCreateRequest, imgPath, encoder.encode(userCreateRequest.getPassword()));
        return userRepository.create(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new UserException(UserErrorCode.EMAIL_NOT_FOUND));
    }

    @Override
    @Transactional
    public User modifyPassword(User user, String password) {
        user.modifyPassword(encoder.encode(password));
        return userRepository.modify(user);
    }

    @Override
    @Transactional
    public User modifyUser(User user, UserModifyRequest dto) {
        user.modifyUserInfo(dto);
        return userRepository.modify(user);
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
}
