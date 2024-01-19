package com.ssafy.goumunity.user.controller;

import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.dto.*;
import com.ssafy.goumunity.user.service.UserService;
import com.ssafy.goumunity.user.service.VertificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final VertificationService vertificationService;

    @Value("${session.key.user}")
    private String SESSION_LOGIN_USER_KEY;

    @PostMapping("/join")
    public ResponseEntity<UserResponse> saveUser(
            @RequestPart(value = "data") @Valid UserCreateDto userCreateDto,
            @RequestPart(value = "image", required = false) MultipartFile profileImage) {
        User user = userService.saveUser(userCreateDto, profileImage);
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
        vertificationService.send(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/verification")
    public ResponseEntity<Boolean> checkVerificationCode(
            @RequestBody @Valid VerificationCodeDto verificationCodeDto) {
        return ResponseEntity.ok(vertificationService.verificate(verificationCodeDto));
    }

    @PutMapping("/my/password")
    public ResponseEntity<Void> modifyPassword(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid PasswordDto passwordDto,
            HttpSession session) {
        User modifiedUser = userService.modifyPassword(user, passwordDto.getPassword());
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
}
