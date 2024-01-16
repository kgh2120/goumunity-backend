package com.ssafy.goumunity.user.service;

import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.dto.UserCreateDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {
    User saveUser(UserCreateDto userCreateDto, MultipartFile profileImage);
    User findUserByEmail(String email);
}
