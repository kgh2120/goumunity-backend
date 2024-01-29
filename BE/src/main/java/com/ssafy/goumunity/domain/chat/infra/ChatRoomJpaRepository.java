package com.ssafy.goumunity.domain.chat.infra;

import java.time.Instant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {

    @Query(
            "select distinct c from ChatRoomEntity c "
                    + " left join c.chatRoomHashtags ch"
                    + " left join ch.hashtag h "
                    + " left join c.host host"
                    + " where (c.title like %:keyword% or  h.name =:keyword) "
                    + " and c.createdAt <= :time "
                    + " order by c.id desc")
    Slice<ChatRoomEntity> searchChatRoom(String keyword, Instant time, Pageable pageable);
}
