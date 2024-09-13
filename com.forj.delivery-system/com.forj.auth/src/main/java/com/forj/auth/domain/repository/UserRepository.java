package com.forj.auth.domain.repository;

import com.forj.auth.domain.model.User;
import com.forj.auth.domain.model.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserRepository {

    boolean existsByUsername(String username);

    User save(User user);

    Optional<User> findByUsername(String username);

    Optional<User> findById(Long id);

    Page<User> findByUsernameContaining(String username, Pageable pageable);

    Page<User> findByRole(UserRole role, Pageable pageable);

    Page<User> findByUsernameContainingAndRole(String username, UserRole role, Pageable pageable);

    Page<User> findAll(Pageable pageable);

}
