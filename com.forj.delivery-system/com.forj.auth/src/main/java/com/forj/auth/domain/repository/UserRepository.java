package com.forj.auth.domain.repository;

import com.forj.auth.domain.model.User;

public interface UserRepository {

    boolean existsByUsername(String username);

    User save(User user);

}
