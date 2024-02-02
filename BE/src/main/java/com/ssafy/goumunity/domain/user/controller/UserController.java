package com.ssafy.goumunity.domain.user.controller;

import com.ssafy.goumunity.common.constraint.Email;
import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.controller.request.PasswordModifyRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserCreateRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserModifyRequest;
import com.ssafy.goumunity.domain.user.controller.request.VerificationCodeRequest;
import com.ssafy.goumunity.domain.user.controller.response.NicknameValidationResponse;
import com.ssafy.goumunity.domain.user.controller.response.UserResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.service.UserService;
import com.ssafy.goumunity.domain.user.service.VerificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final VerificationService verificationService;

    @Value("${session.key.user}")
    private String SESSION_LOGIN_USER_KEY;

    @PostMapping("/join")
    public ResponseEntity<UserResponse> saveUser(
            @RequestPart(value = "data") @Valid UserCreateRequest userCreateRequest,
            @RequestPart(value = "image", required = false) MultipartFile profileImage) {
        User user = userService.saveUser(userCreateRequest, profileImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> findUserByEmail(@PathVariable(value = "email") String email) {
        User user = userService.findUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(user));
    }

    @GetMapping("/email/verification")
    public ResponseEntity<Void> sendVerificationCode(
            @RequestParam("email") @Valid @Email String email) {
        verificationService.send(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/verification")
    public ResponseEntity<Boolean> checkVerificationCode(
            @RequestBody @Valid VerificationCodeRequest verificationCodeRequest) {
        return ResponseEntity.ok(verificationService.verificate(verificationCodeRequest));
    }

    @PutMapping("/my/password")
    public ResponseEntity<Void> modifyPassword(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid PasswordModifyRequest passwordModifyRequest,
            HttpSession session) {
        User modifiedUser = userService.modifyPassword(user, passwordModifyRequest.getPassword());
        session.setAttribute(SESSION_LOGIN_USER_KEY, modifiedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.removeAttribute(SESSION_LOGIN_USER_KEY);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/nickname/validation")
    public ResponseEntity<NicknameValidationResponse> isExistNickname(
            @RequestParam @NotBlank String nickname) {
        return ResponseEntity.ok()
                .body(new NicknameValidationResponse(userService.isExistNickname(nickname)));
    }

    @DeleteMapping("/my")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal User user, HttpSession session) {
        userService.deleteUser(user);
        session.removeAttribute(SESSION_LOGIN_USER_KEY);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my")
    public ResponseEntity<UserResponse> findMyUser(@AuthenticationPrincipal User user) {
        User me = userService.findUserByEmail(user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(UserResponse.from(me));
    }

    @PutMapping("/my")
    public ResponseEntity<Void> ModifyMyUser(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserModifyRequest userModifyRequest,
            HttpSession session) {
        User modifiedUser = userService.modifyUser(user, userModifyRequest);
        session.setAttribute(SESSION_LOGIN_USER_KEY, modifiedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my/chat-rooms")
    public ResponseEntity<SliceResponse<MyChatRoomResponse>> findMyChatRoom(
            @AuthenticationPrincipal User user, Long time, Pageable pageable) {
        Slice<MyChatRoomResponse> res = userService.findMyChatRoom(user, time, pageable);
        return ResponseEntity.ok(SliceResponse.from(res.getContent(), res.hasNext()));
    }
}
