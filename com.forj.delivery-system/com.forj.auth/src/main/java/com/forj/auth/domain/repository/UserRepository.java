package com.forj.auth.domain.repository;

import com.forj.auth.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    boolean existsByUsername(String username);

    User save(User user);

    Optional<User> findByUsername(String username);

}
