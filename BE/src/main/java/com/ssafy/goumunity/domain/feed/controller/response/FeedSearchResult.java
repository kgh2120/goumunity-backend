package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.FeedSearchResource;
import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class FeedSearchResult {
    private List<FeedSearchResource> result;

    public static FeedSearchResult from(List<FeedSearchResource> feedSearchResource) {
        return FeedSearchResult.builder().result(feedSearchResource).build();
    }
}
