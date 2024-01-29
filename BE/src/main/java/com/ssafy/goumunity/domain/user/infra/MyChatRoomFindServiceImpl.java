package com.ssafy.goumunity.domain.user.infra;

import com.ssafy.goumunity.domain.chat.controller.response.MyChatRoomResponse;
import com.ssafy.goumunity.domain.chat.service.ChatRoomService;
import com.ssafy.goumunity.domain.user.domain.User;
import com.ssafy.goumunity.domain.user.service.port.MyChatRoomFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyChatRoomFindServiceImpl implements MyChatRoomFindService {

    private final ChatRoomService chatRoomService;

    @Override
    public Slice<MyChatRoomResponse> findMyChatRoom(User user, Long time, Pageable pageable) {
        return chatRoomService.findMyChatRoom(user, time, pageable);
    }
}
