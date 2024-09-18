package com.forj.ai.domain.repository;

import com.forj.ai.domain.model.Ai;

import java.util.Optional;
import java.util.UUID;

public interface AiRepository {

    Ai save(Ai ai);

    Optional<Ai> findByAiId(UUID aiId);

}
