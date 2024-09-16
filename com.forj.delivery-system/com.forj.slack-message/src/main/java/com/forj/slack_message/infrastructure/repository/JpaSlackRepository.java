package com.forj.slack_message.infrastructure.repository;

import com.forj.slack_message.domain.model.Slack;
import com.forj.slack_message.domain.repository.SlackRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaSlackRepository extends JpaRepository<Slack, UUID>, SlackRepository {

}
