package com.programmers.jpa_board.user.application;

import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.UserResponse;
import com.programmers.jpa_board.global.exception.NotFoundException;
import com.programmers.jpa_board.user.infra.UserRepository;
import com.programmers.jpa_board.user.util.UserConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserProviderService {
    private static final String NOT_FOUND_USER = "회원이 존재하지 않습니다.";

    private final UserRepository userRepository;
    private final UserConverter converter;

    public UserServiceImpl(UserRepository userRepository, UserConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Transactional
    public UserResponse save(CreateUserRequest request) {
        User user = converter.toEntity(request);
        User saved = userRepository.save(user);

        return converter.toDto(saved);
    }

    @Override
    public User getOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));
    }
}
