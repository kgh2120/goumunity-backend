package com.ssafy.goumunity.domain.chat.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomHashtag {

    private Long id;
    private ChatRoom chatRoom;
    private Hashtag hashtag;
    private Integer sequence;
}
