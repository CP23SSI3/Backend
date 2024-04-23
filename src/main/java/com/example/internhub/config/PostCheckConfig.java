package com.example.internhub.config;

import com.example.internhub.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class PostCheckConfig implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private PostService postService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        postService.postCheck();
    }
}
