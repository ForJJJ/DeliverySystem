package com.forj.auth.application.service;

import com.forj.auth.application.dto.request.UserLoginRequestDto;
import com.forj.auth.application.dto.request.UserSignupRequestDto;
import com.forj.auth.application.dto.request.UserUpdateRequestDto;
import com.forj.auth.application.dto.response.UserGetResponseDto;
import com.forj.auth.application.dto.response.UserSearchResponseDto;
import com.forj.auth.domain.model.User;
import com.forj.auth.domain.model.UserRole;
import com.forj.auth.domain.repository.UserRepository;
import com.forj.auth.infrastructure.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Transactional
    public void signup(@Valid UserSignupRequestDto requestDto) {

        if (userRepository.existsByUsername(requestDto.username())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 username입니다.");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.password());

        User user = User.signup(requestDto.username(), encodedPassword, requestDto.role(), requestDto.slackId());
        userRepository.save(user);

    }

    @Transactional
    public String login(UserLoginRequestDto requestDto) {
        User user = verifyUsername(requestDto.username(), requestDto.password());

        return jwtUtil.createToken(user.getUserId(), user.getRole());
    }

    public UserGetResponseDto getUser(Long userId) {
        User user = verifyByUserId(userId);

        return UserGetResponseDto.fromEntity(user);
    }

    @Transactional
    public void updateUser(Long userId, UserUpdateRequestDto requestDto) {
        User user = verifyByUserId(userId);

        if (requestDto.password() != null) {
            String encodedNewPassword = passwordEncoder.encode(requestDto.password());
            user.updatePassword(encodedNewPassword);
        }

        user.updateUsername(requestDto.username());

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = verifyByUserId(userId);

        if (!Objects.equals(userId, getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 아닙니다.");
        }

        user.delete(String.valueOf(userId));
    }

    public Page<UserSearchResponseDto> searchUser(String usernameKeyword, String roleKeyword, Pageable pageable) {
        if (usernameKeyword != null && roleKeyword != null) {
            return userRepository.findByUsernameContainingAndRole(usernameKeyword, UserRole.valueOf(roleKeyword), pageable)
                    .map(UserSearchResponseDto::fromEntity);
        } else if (usernameKeyword != null) {
            return userRepository.findByUsernameContaining(usernameKeyword, pageable)
                    .map(UserSearchResponseDto::fromEntity);
        } else if (roleKeyword != null) {
            return userRepository.findByRole(UserRole.valueOf(roleKeyword), pageable)
                    .map(UserSearchResponseDto::fromEntity);
        } else {
            return userRepository.findAll(pageable)
                    .map(UserSearchResponseDto::fromEntity);
        }
    }

    private User verifyUsername(String username, String password) {
        User user = userRepository.findByUsername(username)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다.");
        }

        return user;
    }

    private User verifyByUserId(Long userId) {
        return userRepository.findById(userId)
                .filter(o -> !o.isDeleted())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."));
    }

    private Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
    }

}
