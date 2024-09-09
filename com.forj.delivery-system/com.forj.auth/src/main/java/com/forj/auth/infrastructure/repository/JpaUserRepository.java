package com.forj.auth.infrastructure.repository;

import com.forj.auth.domain.model.User;
import com.forj.auth.domain.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long>, UserRepository {

}
