package com.ssafy.goumunity.domain.chat.infra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {}
