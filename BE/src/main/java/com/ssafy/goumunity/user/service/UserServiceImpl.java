package com.ssafy.goumunity.user.service;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.image.Image;
import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.domain.UserStatus;
import com.ssafy.goumunity.user.dto.UserCreateDto;
import com.ssafy.goumunity.user.service.port.UserRepository;
import com.ssafy.goumunity.util.SingleImageHandler;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SingleImageHandler imageHandler;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public User saveUser(UserCreateDto userCreateDto, MultipartFile profileImage) {
        // 이메일 중복 검사
        this.isExistEmail(userCreateDto.getEmail());

        Image image = imageHandler.parseFileInfo(profileImage);

        // profileImage가 null일 경우에 imgPath도 null
        String imgPath = image != null ? image.getStoredFilePath() : null;
        User user = User.from(userCreateDto, imgPath, encoder.encode(userCreateDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND));
    }

    @Override
    public void isExistEmail(String email) {
        userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .ifPresent((user) -> {
                    throw new CustomException(CustomErrorCode.EXIST_EMAIL);
                });
    }


}
