package com.ssafy.goumunity.domain.chat.infra;

import com.ssafy.goumunity.domain.chat.domain.Chat;
import com.ssafy.goumunity.domain.chat.service.port.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ChatRepositoryImpl implements ChatRepository {

    private final ChatJpaRepository chatJpaRepository;

    @Override
    public void save(Chat chat) {
        chatJpaRepository.save(ChatEntity.from(chat));
    }
}
