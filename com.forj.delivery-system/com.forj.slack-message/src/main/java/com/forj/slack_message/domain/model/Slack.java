package com.forj.slack_message.domain.model;

import com.forj.common.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "p_slack_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Slack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "slack_message_id")
    private UUID slackMessageId;

    @Column(name = "user_id")
    Long userId; // 수신자

    @Column(name = "message", columnDefinition = "TEXT")
    String message;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    public static Slack createSlackMessage(Long userId, String message, LocalDateTime sendTime) {
        return Slack.builder()
                .userId(userId)
                .message(message)
                .sendTime(sendTime)
                .build();
    }

}
