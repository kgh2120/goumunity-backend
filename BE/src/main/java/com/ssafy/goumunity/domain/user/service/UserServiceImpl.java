package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.domain.UserStatus;
import com.ssafy.goumunity.domain.user.dto.UserCreateDto;
import com.ssafy.goumunity.domain.user.dto.UserUpdateDto;
import com.ssafy.goumunity.domain.user.service.port.UserRepository;
import com.ssafy.goumunity.image.Image;
import com.ssafy.goumunity.util.SingleImageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final SingleImageHandler imageHandler;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public User saveUser(UserCreateDto userCreateDto, MultipartFile profileImage) {
        // 이메일 중복 검사
        if (userRepository.existsByEmail(userCreateDto.getEmail())) {
            throw new CustomException(CustomErrorCode.EXIST_EMAIL);
        }

        Image image = imageHandler.parseFileInfo(profileImage);

        // profileImage가 null일 경우에 imgPath도 null
        String imgPath = image != null ? image.getStoredFilePath() : null;
        User user = User.from(userCreateDto, imgPath, encoder.encode(userCreateDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND));
    }

    @Override
    public boolean isExistEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User modifyPassword(User user, String password) {
        user.modifyPassword(encoder.encode(password));
        return userRepository.modify(user);
    }

    @Override
    @Transactional
    public User modifyUser(User user, UserUpdateDto dto) {
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
}
