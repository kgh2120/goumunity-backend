package com.ssafy.goumunity.config.security;

import com.ssafy.goumunity.common.exception.CustomErrorCode;
import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.user.domain.UserStatus;
import com.ssafy.goumunity.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomDetails(
                userRepository
                        .findByEmailAndStatus(username, UserStatus.ACTIVE)
                        .orElseThrow(() -> new CustomException(CustomErrorCode.EMAIL_NOT_FOUND)));
    }
}
