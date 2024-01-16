package com.ssafy.goumunity.user.controller;

import com.ssafy.goumunity.user.domain.User;
import com.ssafy.goumunity.user.dto.UserCreateDto;
import com.ssafy.goumunity.user.dto.UserResponse;
import com.ssafy.goumunity.user.service.CertificationService;
import com.ssafy.goumunity.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final CertificationService certificationService;

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
    public ResponseEntity<Void> sendMessage(@RequestParam("email") @Valid @Email String email) {
        certificationService.send(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
