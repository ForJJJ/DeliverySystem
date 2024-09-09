package com.forj.hub.domain.repository;

import com.forj.hub.domain.model.Hub;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface HubRepository {

    Hub findById(UUID hubId);
    Hub save(Hub hub);
    Hub findByIdAndIsDeletedFalse(UUID hubId);
}
