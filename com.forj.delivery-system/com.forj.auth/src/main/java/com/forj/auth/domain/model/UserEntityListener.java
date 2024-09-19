package com.forj.auth.domain.model;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PostLoad;

public class UserEntityListener {

    @PostLoad
    public void filterDeleted(User user) {
        if (user.isDeleted()) {
            throw new EntityNotFoundException("삭제된 유저입니다.");
        }
    }

}
