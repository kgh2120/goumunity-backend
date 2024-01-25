package com.ssafy.goumunity.domain.chat.service;

import com.ssafy.goumunity.domain.chat.controller.request.ChatRoomRequest;
import com.ssafy.goumunity.domain.chat.domain.ChatRoom;
import com.ssafy.goumunity.domain.chat.exception.ChatErrorCode;
import com.ssafy.goumunity.domain.chat.exception.ChatException;
import com.ssafy.goumunity.domain.chat.service.port.ChatRoomRepository;
import com.ssafy.goumunity.domain.chat.service.port.ImageUploadService;
import com.ssafy.goumunity.domain.chat.service.port.RegionFindService;
import com.ssafy.goumunity.domain.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final HashtagService hashtagService;
    private final ChatRoomRepository chatRoomRepository;
    private final ImageUploadService imageUploadService;
    private final RegionFindService regionFindService;

    @Override
    public void createChatRoom(ChatRoomRequest.Create dto, MultipartFile multipartFile, User user) {
        // 해시 검색
        verifyCreateChatRoom(dto);
        // 채팅방 생성
        chatRoomRepository.save(
                ChatRoom.create(
                        dto,
                        dto.getRegionId(),
                        user.getId(),
                        imageUploadService.uploadImage(multipartFile),
                        convertHashtagIds(dto)));
    }

    private void verifyCreateChatRoom(ChatRoomRequest.Create dto) {
        verifyRegionId(dto);
        verifyHashtagIds(dto);
    }

    private void verifyRegionId(ChatRoomRequest.Create dto) {
        if (!regionFindService.isExistsRegion(dto.getRegionId())) {
            throw new ChatException(ChatErrorCode.REGION_ID_NOT_MATCHED);
        }
    }

    private List<Long> convertHashtagIds(ChatRoomRequest.Create dto) {
        return dto.getHashtags().stream().map(ChatRoomRequest.HashtagRequest::getId).toList();
    }

    private void verifyHashtagIds(ChatRoomRequest.Create dto) {
        List<ChatRoomRequest.HashtagRequest> list =
                dto.getHashtags().stream()
                        .filter(h -> hashtagService.existsOneByHashtagId(h.getId()))
                        .toList();

        if (list.size() != dto.getHashtags().size()) {
            throw new ChatException(ChatErrorCode.HASHTAG_NOT_FOUND);
        }
    }
}
