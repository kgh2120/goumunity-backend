package com.ssafy.goumunity.common.config.contextchecker;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContextCheckerImpl {
    @Value("${ymlchecker}")
    private String checker;

    @PostConstruct
    public void checkYml() {
        log.info("----------------Context From: " + checker + "-------------------");
    }
}
