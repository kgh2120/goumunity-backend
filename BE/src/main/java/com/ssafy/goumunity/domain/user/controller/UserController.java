package com.ssafy.goumunity.domain.user.controller;

import com.ssafy.goumunity.common.constraint.Email;
import com.ssafy.goumunity.common.util.SliceResponse;
import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.feed.controller.response.FeedSearchResult;
import com.ssafy.goumunity.domain.feed.controller.response.SavingResult;
import com.ssafy.goumunity.domain.feed.service.FeedService;
import com.ssafy.goumunity.domain.user.controller.request.PasswordModifyRequest;
import com.ssafy.goumunity.domain.user.controller.request.ProfileImageModifyRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserRequest;
import com.ssafy.goumunity.domain.user.controller.request.VerificationCodeRequest;
import com.ssafy.goumunity.domain.user.controller.response.NicknameValidationResponse;
import com.ssafy.goumunity.domain.user.controller.response.UserRankingResponse;
import com.ssafy.goumunity.domain.user.controller.response.UserResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.service.UserService;
import com.ssafy.goumunity.domain.user.service.VerificationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
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

    private final FeedService feedService;

    @Value("${session.key.user}")
    private String SESSION_LOGIN_USER_KEY;

    @PostMapping("/join")
    public ResponseEntity<Long> createUser(
            @RequestPart(value = "data") @Valid UserRequest.Create userRequest,
            @RequestPart(value = "image", required = false) MultipartFile profileImage) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userRequest, profileImage));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok().body(UserResponse.from(userService.findUserByUserId(userId)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> findUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(UserResponse.from(userService.findUserByEmail(email)));
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<UserResponse> findUserByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok().body(UserResponse.from(userService.findUserByNickname(nickname)));
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

    @PatchMapping("/my/password")
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

    @PatchMapping("/my")
    public ResponseEntity<Void> ModifyMyUser(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UserRequest.Modify userModifyRequest,
            HttpSession session) {
        User modifiedUser = userService.modifyUser(user, userModifyRequest);
        session.setAttribute(SESSION_LOGIN_USER_KEY, modifiedUser);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/my/profile-images")
    public ResponseEntity<String> createProfileImage(
            @RequestParam("image") MultipartFile profileImage) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createProfileImage(profileImage));
    }

    @PatchMapping("/my/profile-images")
    public ResponseEntity<Void> modifyProfileImage(
            @AuthenticationPrincipal User user,
            @RequestBody ProfileImageModifyRequest profileImageModifyRequest,
            HttpSession session) {
        User modifiedUser = userService.modifyProfileImage(user, profileImageModifyRequest.getImgSrc());
        session.setAttribute(SESSION_LOGIN_USER_KEY, modifiedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my/chat-rooms")
    public ResponseEntity<SliceResponse<MyChatRoomResponse>> findMyChatRoom(
            @AuthenticationPrincipal User user, Long time, Pageable pageable) {
        Slice<MyChatRoomResponse> res = userService.findMyChatRoom(user, time, pageable);
        return ResponseEntity.ok(SliceResponse.from(res.getContent(), res.hasNext()));
    }

    @GetMapping("/{userId}/feeds")
    public ResponseEntity<FeedSearchResult> findAllFeedByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(feedService.findAllFeedByUserId(userId));
    }

    @GetMapping("/{userId}/savings")
    public ResponseEntity<SavingResult> findAllSavingByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(feedService.findAllSavingByUserId(userId));
    }

    @GetMapping("/{userId}/scraps")
    public ResponseEntity<FeedSearchResult> findAllScrappedFeedByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(feedService.findAllScrappedFeedByUserId(userId));
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<UserRankingResponse>> findUserRanking() {
        return ResponseEntity.ok(userService.findUserRanking());
    }
}
