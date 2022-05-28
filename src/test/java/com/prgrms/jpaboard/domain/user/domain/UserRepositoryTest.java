package com.prgrms.jpaboard.domain.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("jerry")
                .age(25)
                .hobby("누워 있기")
                .createdBy("jerry")
                .build();

        userRepository.save(user);
    }

    @Test
    @DisplayName("user 생성 테스트")
    void testCreate() {
        Optional<User> retUser = userRepository.findById(user.getId());

        assertThat(retUser).isNotEmpty();
        assertThat(retUser.get().getCreatedBy()).isEqualTo(user.getCreatedBy());
        assertThat(retUser.get().getCreatedAt()).isNotNull();
        assertThat(retUser.get().getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("수정 테스트")
    void testUpdate() {
        User retUser = userRepository.findById(user.getId()).get();
        retUser.updateAge(26);

        User retUser2 = userRepository.findById(user.getId()).get();
        assertThat(retUser2.getAge()).isEqualTo(26);
    }

    @Test
    @DisplayName("삭제 테스트")
    void testDelete() {
        userRepository.delete(user);

        Optional<User> retUser = userRepository.findById(user.getId());
        assertThat(retUser).isEmpty();
    }
}