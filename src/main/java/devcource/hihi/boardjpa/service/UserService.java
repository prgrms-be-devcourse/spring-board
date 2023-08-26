package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.user.CreateUserDto;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import devcource.hihi.boardjpa.dto.user.UpdateUserDto;
import devcource.hihi.boardjpa.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Page<User> getUsers(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAll(pageable);
    }

    public ResponseUserDto createUser(CreateUserDto userDto) {
        User user = userDto.toEntity();
        User savedUser = userRepository.save(user);
        return User.toDtoForResponse(savedUser);
    }

    public ResponseUserDto getUser(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("id에 해당하는 user는 없습니다."));
        return User.toDtoForResponse(findUser);
    }

    public ResponseUserDto updateUser(Long id, UpdateUserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(() ->  new RuntimeException("id에 해당하는 user는 없습니다."));
        user.changeName(userDto.name());
        user.changeAge(userDto.age());
        user.changeHobby(userDto.hobby());
        return User.toDtoForResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
