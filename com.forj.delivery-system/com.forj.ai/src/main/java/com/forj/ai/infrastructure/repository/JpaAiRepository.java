package com.forj.ai.infrastructure.repository;

import com.forj.ai.domain.model.Ai;
import com.forj.ai.domain.repository.AiRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaAiRepository extends JpaRepository<Ai, UUID>, AiRepository {

}
