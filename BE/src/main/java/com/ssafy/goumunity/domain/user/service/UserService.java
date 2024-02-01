package com.ssafy.goumunity.domain.user.service;

import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.controller.request.UserCreateRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserModifyRequest;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User saveUser(UserCreateRequest userCreateRequest, MultipartFile profileImage);

    User findUserByEmail(String email);

    boolean isExistEmail(String email);

    User modifyPassword(User user, String password);

    User modifyUser(User user, UserModifyRequest dto);

    boolean isExistNickname(String nickname);

    void deleteUser(User user);

    Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable);
}
