package com.forj.slack_message.application.dto.request;

import java.util.HashMap;

public record SlackMessageRequestDto(

        Long userId,
        HashMap<String, String> data

) {

}
