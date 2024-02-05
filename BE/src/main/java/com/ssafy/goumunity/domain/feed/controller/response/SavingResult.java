package com.ssafy.goumunity.domain.feed.controller.response;

import com.ssafy.goumunity.domain.feed.domain.SavingResource;
import java.util.List;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class SavingResult {
    private List<SavingResource> result;

    public static SavingResult from(List<SavingResource> savingResources) {
        return SavingResult.builder().result(savingResources).build();
    }
}
