package com.ssafy.goumunity.domain.user.domain;

import com.ssafy.goumunity.common.exception.CustomException;
import com.ssafy.goumunity.domain.user.controller.request.UserCreateRequest;
import com.ssafy.goumunity.domain.user.controller.request.UserModifyRequest;
import com.ssafy.goumunity.domain.user.exception.UserErrorCode;
import com.ssafy.goumunity.domain.user.exception.UserException;
import java.time.Instant;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"lastPasswordModifiedDate", "createdAt", "updatedAt"})
public class User {

    private Long id;
    private String email;
    private String password;
    private Long monthBudget;
    private Integer age;
    private UserCategory userCategory;
    private Integer gender;
    private String nickname;
    private String imgSrc;
    private Instant registerDate;
    private UserStatus userStatus;
    private Instant lastPasswordModifiedDate;
    private Integer regionId;
    private Instant createdAt;
    private Instant updatedAt;

    public static User from(UserCreateRequest userCreateRequest, String imgUrl, String encodedPw) {
        return User.builder()
                .email(userCreateRequest.getEmail())
                .password(encodedPw)
                .monthBudget(userCreateRequest.getMonthBudget())
                .age(userCreateRequest.getAge())
                .userCategory(userCreateRequest.getUserCategory())
                .gender(userCreateRequest.getGender())
                .nickname(userCreateRequest.getNickname())
                .imgSrc(imgUrl)
                .registerDate(Instant.now())
                .userStatus(UserStatus.ACTIVE)
                .lastPasswordModifiedDate(Instant.now())
                .regionId(userCreateRequest.getRegionId())
                .build();
    }

    public void modifyPassword(String password) {
        this.password = password;
    }

    /**
     * 전달받은 파라미터의 필드에 값이 있다면, 필드에 대응하는 User 클래스의 정보를 변경한다. 모든 값이 비어있다면 예외가 발생한다.
     *
     * @param dto
     * @throws CustomException 파라미터의 모든 필드가 비어있으면 예외 발생
     */
    public void modifyUserInfo(UserModifyRequest dto) {
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
    }

    public void deleteUser() {
        this.userStatus = UserStatus.DELETED;
    }
}
