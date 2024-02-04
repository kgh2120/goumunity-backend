package com.ssafy.goumunity.domain.chat.infra.chat;

import com.ssafy.goumunity.domain.chat.controller.response.MessageResponse;
import com.ssafy.goumunity.domain.chat.domain.Chat;
import com.ssafy.goumunity.domain.chat.service.port.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRepositoryImpl implements ChatRepository {

    private final ChatJpaRepository chatJpaRepository;
    private final ChatQueryDslRepository chatQueryDslRepository;

    @Override
    public void save(Chat chat) {
        chatJpaRepository.save(ChatEntity.from(chat));
    }

    @Override
    public Slice<MessageResponse.Previous> findPreviousMessage(
            Long chatroomId, Long time, Pageable pageable, Long userId) {
        return chatQueryDslRepository.findPreviousMessage(chatroomId, time, pageable, userId);
    }
}
