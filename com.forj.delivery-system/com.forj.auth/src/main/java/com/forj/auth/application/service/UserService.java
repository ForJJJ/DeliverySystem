package com.forj.auth.application.service;

import com.forj.auth.application.dto.request.UserSignupRequestDto;
import com.forj.auth.domain.model.User;
import com.forj.auth.domain.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    private final PasswordEncoder passwordEncoder;

    public void signup(@Valid UserSignupRequestDto requestDto) {

        if (userRepository.existsByUsername(requestDto.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 username입니다.");
        }

//        String encodedPassword = passwordEncoder.encode(requestDto.password());

        User user = User.signup(requestDto.username(), requestDto.password(), requestDto.role());
        userRepository.save(user);

    }

}
