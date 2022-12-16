package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.CreateUserRequest;
import com.prgrms.jpa.domain.User;
import com.prgrms.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prgrms.jpa.utils.ToEntityMapper.toUser;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public long create(CreateUserRequest createUserRequest) {
        User user = userRepository.save(toUser(createUserRequest));
        return user.getId();
    }
}
