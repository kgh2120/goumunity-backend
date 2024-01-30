package com.ssafy.goumunity.domain.user.service.port;

import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MyChatRoomFindService {
    Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable);
}
