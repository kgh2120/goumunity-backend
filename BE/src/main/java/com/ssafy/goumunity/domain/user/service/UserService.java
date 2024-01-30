package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.dto.UserCreateDto;
import com.ssafy.goumunity.domain.user.dto.UserUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User saveUser(UserCreateDto userCreateDto, MultipartFile profileImage);

    User findUserByEmail(String email);

    boolean isExistEmail(String email);

    User modifyPassword(User user, String password);

    User modifyUser(User user, UserUpdateDto dto);

    boolean isExistNickname(String nickname);

    void deleteUser(User user);

    Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable);
}
