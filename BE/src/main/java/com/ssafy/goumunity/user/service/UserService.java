package com.ssafy.goumunity.user.service;

import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.dto.UserCreateDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User saveUser(UserCreateDto userCreateDto, MultipartFile profileImage);

    User findUserByEmail(String email);

    boolean isExistEmail(String email);

    User modifyPassword(User user, String password);

    User modifyUser(User user);

    boolean isExistNickname(String nickname);

    void deleteUser(User user);
}
