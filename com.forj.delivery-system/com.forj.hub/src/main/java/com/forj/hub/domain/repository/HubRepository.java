package com.forj.hub.domain.repository;

import com.forj.hub.domain.model.Hub;

import java.util.List;
import java.util.UUID;

public interface HubRepository {

    Hub findById(UUID hubId);
    Hub save(Hub hub);
    Hub findByIdAndIsDeletedFalse(UUID hubId);
    List<Hub> findAll();
}
