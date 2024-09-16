package com.forj.slack_message.presentation.controller;

import com.forj.slack_message.application.dto.request.SlackMessageRequestDto;
import com.forj.slack_message.application.service.SlackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/slacks")
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    @PostMapping
    public ResponseEntity<Void> sendSlackMessage(@RequestBody SlackMessageRequestDto requestDto) {
        slackService.sendSlackMessage(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
