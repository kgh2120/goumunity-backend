package com.ssafy.goumunity.user.controller;

import com.ssafy.goumunity.config.security.CustomDetails;
import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.dto.PasswordDto;
import com.ssafy.goumunity.user.dto.UserCreateDto;
import com.ssafy.goumunity.user.dto.UserResponse;
import com.ssafy.goumunity.user.dto.VerificationCodeDto;
import com.ssafy.goumunity.user.service.UserService;
import com.ssafy.goumunity.user.service.VertificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/join")
    public ResponseEntity<UserResponse> saveUser(@RequestPart(value = "data") @Valid UserCreateDto userCreateDto,
                                                   @RequestPart(value = "image", required = false) MultipartFile profileImage){
        User user = userService.saveUser(userCreateDto, profileImage);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.from(user));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> findUserByEmail(@PathVariable(value = "email") String email){
        User user = userService.findUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(UserResponse.from(user));
    }

    @GetMapping("/email/verification")
    public ResponseEntity<Void> sendVerificationCode(@RequestParam("email") @Valid @Email String email) {
        vertificationService.send(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/email/verification")
    public ResponseEntity<Boolean> checkVerificationCode(@RequestBody @Valid VerificationCodeDto verificationCodeDto){
        return ResponseEntity.ok(vertificationService.verificate(verificationCodeDto));
    }

    @PutMapping("/my/password")
    public ResponseEntity<Void> modifyPassword(@AuthenticationPrincipal CustomDetails userDetails,
                                               @RequestBody @Valid PasswordDto passwordDto){
        userService.modifyPassword(userDetails.getUser(), passwordDto.getPassword());
        return ResponseEntity.ok().build();
    }
}
