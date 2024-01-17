package com.ssafy.goumunity.user.infrastructure;

import com.ssafy.goumunity.user.domain.UserCategory;
import com.ssafy.goumunity.user.domain.UserStatus;
import com.ssafy.goumunity.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "month_budget")
    private Long monthBudget;

    @Column(name = "age")
    private Integer age;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private UserCategory userCategory;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "img_src")
    private String imgSrc;

    @Column(name = "register_date")
    private Instant registerDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "last_password_modified_date")
    private Instant lastPasswordModifiedDate;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public static UserEntity fromModel(User user){
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .monthBudget(user.getMonthBudget())
                .age(user.getAge())
                .userCategory(user.getUserCategory())
                .gender(user.getGender())
                .nickname(user.getNickname())
                .imgSrc(user.getImgSrc())
                .registerDate(user.getRegisterDate())
                .userStatus(user.getUserStatus())
                .lastPasswordModifiedDate(user.getLastPasswordModifiedDate())
                .regionId(user.getRegionId())
                .build();
    }

    public User toModel(){
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .monthBudget(this.monthBudget)
                .age(this.age)
                .userCategory(this.userCategory)
                .gender(this.gender)
                .nickname(this.nickname)
                .imgSrc(this.imgSrc)
                .registerDate(this.registerDate)
                .userStatus(this.userStatus)
                .lastPasswordModifiedDate(this.lastPasswordModifiedDate)
                .regionId(this.regionId)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
