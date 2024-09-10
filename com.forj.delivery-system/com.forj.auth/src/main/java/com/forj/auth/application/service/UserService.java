package com.forj.auth.application.service;

import com.forj.auth.application.dto.request.UserLoginRequestDto;
import com.forj.auth.application.dto.request.UserSignupRequestDto;
import com.forj.auth.domain.model.User;
import com.forj.auth.domain.repository.UserRepository;
import com.forj.auth.infrastructure.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public void signup(@Valid UserSignupRequestDto requestDto) {

        if (userRepository.existsByUsername(requestDto.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 username입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.password());

        User user = User.signup(requestDto.username(), encodedPassword, requestDto.role());
        userRepository.save(user);

    }

    public String login(UserLoginRequestDto requestDto) {
        User user = verifyUser(requestDto.username(), requestDto.password());

        return jwtUtil.createToken(requestDto.username(), user.getRole());
    }

    private User verifyUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다.");
        }

        return user;
    }

}
