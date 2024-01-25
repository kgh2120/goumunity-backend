package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.domain.ChatRoomHashtag;
import jakarta.persistence.*;
import lombok.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "chat_room_hashtag")
@Entity
public class ChatRoomHashtagEntity {

    @Id
    @Column(name = "chat_room_hashtag_id")
    @GeneratedValue
    private Long id;

    @Column(name = "sequence")
    private Integer sequence;

    @JoinColumn(name = "hashtag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private HashtagEntity hashtag;

    @JoinColumn(name = "chat_room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoomEntity chatRoom;

    public static ChatRoomHashtagEntity from(ChatRoomHashtag chatRoomHashtag) {
        return ChatRoomHashtagEntity.builder()
                .id(chatRoomHashtag.getId())
                .sequence(chatRoomHashtag.getSequence())
                .build();
    }

    public ChatRoomHashtag to() {
        return ChatRoomHashtag.builder().id(this.id).sequence(this.sequence).build();
    }
}
