package com.ssafy.goumunity.domain.region.exception;

import com.ssafy.goumunity.common.exception.CustomException;

public class RegionException extends CustomException {
    public RegionException(RegionErrorCode errorCode) {
        super(errorCode);
    }
}
