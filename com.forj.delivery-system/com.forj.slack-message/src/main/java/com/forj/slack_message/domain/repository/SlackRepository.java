package com.forj.slack_message.domain.repository;

import com.forj.slack_message.domain.model.Slack;

public interface SlackRepository {

    Slack save(Slack slack);

}
