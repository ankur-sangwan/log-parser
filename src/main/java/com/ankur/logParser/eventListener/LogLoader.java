package com.ankur.logParser.eventListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LogLoader implements ApplicationRunner {
    @Autowired
    private FileReader fileService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fileService.readFileFromResources("log.txt");
    }
}
