package com.prgrms.springbootboardjpa.user.service;

import com.prgrms.springbootboardjpa.exception.exceptions.DuplicateException;
import com.prgrms.springbootboardjpa.exception.exceptions.NoSuchResourceException;
import com.prgrms.springbootboardjpa.exception.exceptions.WrongPasswordException;
import com.prgrms.springbootboardjpa.user.dto.UserDto;
import com.prgrms.springbootboardjpa.user.dto.UserResponse;
import com.prgrms.springbootboardjpa.user.entity.Password;
import com.prgrms.springbootboardjpa.user.entity.User;
import com.prgrms.springbootboardjpa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public UserResponse save(UserDto userDto){
        User user = UserDto.convertToUser(userDto);
        checkUserDuplicate(user);
        user.setPassword(new Password(encodePassword(user.getPassword().toString())));
        User savedUser = userRepository.save(user);
        return UserResponse.convertToUserResponse(savedUser);
    }

    public void checkUserDuplicate(User user){
        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new DuplicateException("Email이 중복됩니다.");

        if (userRepository.findByNickName(user.getNickName()) != null)
            throw new DuplicateException("Nickname이 중복됩니다.");
    }

    public String encodePassword(String password){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public boolean login(UserDto userDto){
        User user = UserDto.convertToUser(userDto);
        User foundUser = userRepository.findByNickName(user.getNickName());

        if (foundUser == null){
            throw new NoSuchResourceException("해당하는 User 정보가 없습니다.");
        }

        if (!checkPassword(foundUser, user.getPassword().getPassword()))
            throw new WrongPasswordException("Password가 일치하지 않습니다.");

        return true;
    }

    public boolean checkPassword(User findUser, String password){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        if (!passwordEncoder.matches(password,findUser.getPassword().getPassword())){
            return false;
        }
        return true;
    }

    public Page<UserResponse> findAll(Pageable pageable){
        return userRepository.findAll(pageable).map(user -> UserResponse.convertToUserResponse(user));
    }
}
