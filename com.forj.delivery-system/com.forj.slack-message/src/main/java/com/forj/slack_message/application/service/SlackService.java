package com.forj.slack_message.application.service;

import com.forj.slack_message.application.dto.request.SlackMessageRequestDto;
import com.forj.slack_message.application.dto.request.SlackMessageResponseDto;
import com.forj.slack_message.domain.repository.SlackRepository;
import com.forj.slack_message.infrastructure.dto.UserDto;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackRepository slackRepository;

    private final UserServiceClient userServiceClient;

    private final AiServiceClient aiServiceClient;

    @Value("${webhook.slack.bot.token}")
    private String SLACK_BOT_TOKEN;

    private final Slack slackClient = Slack.getInstance();

    // 봇으로 DM 보내기
    public void sendSlackMessage(SlackMessageRequestDto requestDto) {
        try {

            UserDto userDto = userServiceClient.getSlackIdByUserId(requestDto.userId());
            String slackUserId = userDto.slackId();
            String messageText = requestDto.message();

            ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                    .channel(slackUserId)
                    .text(messageText)
                    .build();

            ChatPostMessageResponse response = slackClient.methods(SLACK_BOT_TOKEN).chatPostMessage(messageRequest);

            if (response.isOk()) {
                Long currentUserId = requestDto.userId();
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

    // 업체 배송 담당자에게 보낼 DM
//    @Scheduled(cron = "0 23 1 * * *")
//    public void scheduleMessageForCompanyAgent() {
//        // Ai 서비스로부터 업체 배송 담당자에게 보낼 메시지들을 요청
//        List<SlackMessageResponseDto> messageResponseDtos = aiServiceClient.getInfoForCompanyDeliveryAgent();
//
//        // 각 업체 배송 담당자에게 메시지 전송
//        for (SlackMessageResponseDto responseDto : messageResponseDtos) {
//            SlackMessageRequestDto requestDto = convertToRequestDto(responseDto);
//            sendSlackMessage(requestDto);
//        }
//    }

    @Scheduled(cron = "0 12 2 * * *")
    public void scheduleMessageForCompanyAgent() {
        String userId = "1"; // 필요한 사용자 ID
        String role = "MASTER"; // 필요한 역할

        List<SlackMessageResponseDto> messageResponseDtos = aiServiceClient.getInfoForCompanyDeliveryAgent(userId, role);

        if (messageResponseDtos != null) {
            for (SlackMessageResponseDto responseDto : messageResponseDtos) {
                SlackMessageRequestDto requestDto = convertToRequestDto(responseDto);
                sendSlackMessage(requestDto);
            }
        }
    }

    // 허브 배송 담당자에게 보낼 DM 예약
    @Transactional
//    @Scheduled(cron = "0 0 8 * * *")
    public void scheduleMessageForHubAgent(Long deliveryAgentId) {
        String message = aiServiceClient.getInfoForHubDeliveryAgent(deliveryAgentId);
        SlackMessageRequestDto requestDto = new SlackMessageRequestDto(deliveryAgentId, message);
        sendSlackMessage(requestDto);
    }

    private SlackMessageRequestDto convertToRequestDto(SlackMessageResponseDto responseDto) {
        return new SlackMessageRequestDto(
                responseDto.deliveryAgentId(),
                responseDto.message()
        );
    }

    private Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
