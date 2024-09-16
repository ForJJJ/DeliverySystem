package com.forj.slack_message.application.service;

import com.forj.slack_message.application.dto.request.SlackMessageRequestDto;
import com.forj.slack_message.domain.repository.SlackRepository;
import com.forj.slack_message.infrastructure.dto.UserDto;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackRepository slackRepository;

    private final UserServiceClient userServiceClient;

//    @Value("${webhook.slack.url}")
//    private String SLACK_WEBHOOK_URL;

    @Value("${webhook.slack.bot.token}")
    private String SLACK_BOT_TOKEN;

//    @Value("${webhook.slack.token}")
//    private String slackToken;

    private final Slack slackClient = Slack.getInstance();

    // 슬렉 채널에 메시지 전송
//    public void sendSlackMessage(SlackMessageRequestDto requestDto) {
//        try {
//            slackClient.send(SLACK_WEBHOOK_URL, payload(p -> p
//                    .text("New message") // 메시지 제목
//                    .attachments(List.of(
//                            Attachment.builder().color("#36a64f") // 메시지 색상 (초록색 Hex 코드)
//                                    .fields( // 메시지 본문 내용
//                                            requestDto.data().keySet().stream()
//                                                    .map(key -> generateSlackField(key, requestDto.data().get(key)))
//                                                    .collect(Collectors.toList())
//                                    ).build())))
//            );
//
//            com.forj.slack_message.domain.model.Slack slackMessage
//                    = com.forj.slack_message.domain.model.Slack.createSlackMessage(getCurrentUserId(),
//                    String.join(", ", requestDto.data().values()),
//                    LocalDateTime.now());
//
//            slackRepository.save(slackMessage);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void sendSlackMessage(SlackMessageRequestDto requestDto) {
        try {

            UserDto userDto = userServiceClient.getSlackIdByUserId(requestDto.userId()/*, token*/);
            String slackUserId = userDto.slackId();
            String messageText = requestDto.data().values().stream()
                    .reduce((msg1, msg2) -> msg1 + "\n" + msg2)
                    .orElse("No message content");

            ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                    .channel(slackUserId)
                    .text(messageText)
                    .build();

            ChatPostMessageResponse response = slackClient.methods(SLACK_BOT_TOKEN).chatPostMessage(messageRequest);

            if (response.isOk()) {
                Long currentUserId = getCurrentUserId();
                com.forj.slack_message.domain.model.Slack slackMessage = com.forj.slack_message.domain.model.Slack.createSlackMessage(
                        currentUserId,
                        messageText,
                        LocalDateTime.now()
                );
                slackRepository.save(slackMessage);
            } else {
                throw new RuntimeException("Failed to send message: " + response.getError());
            }

        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }

    }

//        private Field generateSlackField (String title, String value){
//            return Field.builder()
//                    .title(title)
//                    .value(value)
//                    .valueShortEnough(false)
//                    .build();
//        }

//    private String getUserSlackId(Long userId) {
//        return "user-slack-id"; // Replace with actual logic
//    }

    private Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }

//    public String getCurrentUserToken() {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes != null) {
//            HttpServletRequest request = attributes.getRequest();
//            String authHeader = request.getHeader("Authorization");
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                return authHeader.substring(7);
//            }
//        }
//        return null;
//    }

}
