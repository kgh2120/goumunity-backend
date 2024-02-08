package com.ssafy.goumunity.domain.user.domain;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.user.controller.request.UserRequest;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.time.Instant;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = {"lastPasswordModifiedDate", "createdAt", "updatedAt"})
public class User {

    private Long id;
    private String email;
    private String password;
    private Long monthBudget;
    private Integer age;
    private UserCategory userCategory;
    private Gender gender;
    private String nickname;
    private String imgSrc;
    private UserStatus userStatus;
    private Boolean isAuthenticated;
    private Instant lastPasswordModifiedDate;
    private Long regionId;
    private Instant createdAt;
    private Instant updatedAt;

    public static User create(UserRequest.Create userRequest, String imgUrl, String encodedPw) {
        return User.builder()
                .email(userRequest.getEmail())
                .password(encodedPw)
                .monthBudget(userRequest.getMonthBudget())
                .age(userRequest.getAge())
                .userCategory(userRequest.getUserCategory())
                .gender(userRequest.getGender())
                .nickname(userRequest.getNickname())
                .imgSrc(imgUrl)
                .userStatus(UserStatus.ACTIVE)
                .isAuthenticated(false)
                .lastPasswordModifiedDate(Instant.now())
                .regionId(userRequest.getRegionId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    public void modifyPassword(String password) {
        this.password = password;
        this.lastPasswordModifiedDate = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void modifyProfileImage(String imgSrc) {
        this.imgSrc = imgSrc;
        this.updatedAt = Instant.now();
    }

    public void modifyIsAuthenticatedToTrue() {
        this.isAuthenticated = true;
        this.updatedAt = Instant.now();
    }

    /**
     * 전달받은 파라미터의 필드에 값이 있다면, 필드에 대응하는 User 클래스의 정보를 변경한다. 모든 값이 비어있다면 예외가 발생한다.
     *
     * @param dto
     * @throws CustomException 파라미터의 모든 필드가 비어있으면 예외 발생
     */
    public void modifyUserInfo(UserRequest.Modify dto) {
        boolean emptyCheckFlag = true;
        if (dto.getMonthBudget() != null) {
            this.monthBudget = dto.getMonthBudget();
            emptyCheckFlag = false;
        }
        if (dto.getAge() != null) {
            this.age = dto.getAge();
            emptyCheckFlag = false;
        }
        if (dto.getUserCategory() != null) {
            this.userCategory = dto.getUserCategory();
            emptyCheckFlag = false;
        }
        if (dto.getNickname() != null) {
            this.nickname = dto.getNickname();
            emptyCheckFlag = false;
        }
        if (dto.getRegionId() != null) {
            this.regionId = dto.getRegionId();
            emptyCheckFlag = false;
        }

        if (emptyCheckFlag) {
            throw new UserException(UserErrorCode.NO_INPUT_FOR_MODIFY_USER_INFO);
        }
        this.updatedAt = Instant.now();
    }

    public void deleteUser() {
        this.userStatus = UserStatus.DELETED;
    }
}
