package com.programmers.board.service;

import com.programmers.board.domain.User;
import com.programmers.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Long login(String name) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다"));
        return user.getId();
    }
}
