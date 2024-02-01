package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.region.infra.RegionEntity;
import com.ssafy.goumunity.domain.user.infra.UserEntity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.List;
import lombok.*;

@Getter
@Entity
@Builder
@Table(name = "chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoomEntity {

    @Id
    @Column(name = "chat_room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_official")
    private Boolean isOfficial;

    @Column(name = "title", length = 20)
    private String title;

    @Column(name = "capability")
    private Integer capability;

    @Column(name = "img_src")
    private String imgSrc;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(
            name = "updated_at",
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @JoinColumn(name = "region_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private RegionEntity region;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity host;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<ChatRoomHashtagEntity> chatRoomHashtags;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.REMOVE)
    private List<UserChatRoomEntity> userChatRooms;

    public static ChatRoomEntity from(ChatRoom chatRoom) {
        ChatRoomEntityBuilder chatRoomEntityBuilder =
                ChatRoomEntity.builder()
                        .id(chatRoom.getId())
                        .isOfficial(chatRoom.getIsOfficial())
                        .title(chatRoom.getTitle())
                        .capability(chatRoom.getCapability())
                        .imgSrc(chatRoom.getImgSrc());
        // TODO UserEntity, ReginEntity 추가 필요

        if (chatRoom.getCreatedAt() != null) chatRoomEntityBuilder.createdAt(chatRoom.getCreatedAt());
        if (chatRoom.getUpdatedAt() != null) chatRoomEntityBuilder.updatedAt(chatRoom.getUpdatedAt());

        return chatRoomEntityBuilder.build();
    }

    public ChatRoom to() {
        return ChatRoom.builder()
                .id(this.id)
                .isOfficial(this.isOfficial)
                .title(this.title)
                .capability(this.capability)
                .imgSrc(this.imgSrc)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .currentUserCount(userChatRooms.size())
                // TODO 이후 추가 필요
                //                                .region()
                .userId(host == null ? null : this.host.getId())
                .build();
    }

    public void associatedWithUserEntity(UserEntity userEntity) {
        this.host = userEntity;
    }

    public void associatedWithRegionEntity(RegionEntity region) {
        this.region = region;
    }

    public static ChatRoomEntity chatRoomEntityOnlyWithId(Long chatRoomId) {
        return ChatRoomEntity.builder().id(chatRoomId).build();
    }
}
