package com.ssafy.goumunity.domain.chat.domain;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomHashtag {

    private Long id;
    private ChatRoom chatRoom;
    private Hashtag hashtag;
    private Integer sequence;
}
