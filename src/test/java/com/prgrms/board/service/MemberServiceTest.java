package com.prgrms.board.service;

import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.MemberResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    private MemberCreateDto memberCreateDto;

    @BeforeEach
    void setup() {
        memberCreateDto = MemberCreateDto.builder()
                .name("giseo")
                .age(26)
                .hobby("농구")
                .build();
    }

    @Test
    @DisplayName("회원 가입에 성공한다.")
    void 회원_저장_성공() {
        //given
        Long savedMemberId = memberService.join(memberCreateDto);

        //when
        assertDoesNotThrow(() -> memberService.findById(savedMemberId));
        List<MemberResponseDto> responseDtoList = memberService.findAll();

        //then
        assertThat(responseDtoList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("이미 사용중인 이름으로 회원 가입 시 예외가 발생한다.")
    void 회원_저장_실패() {
        memberService.join(memberCreateDto);

        MemberCreateDto sameNameDto = MemberCreateDto.builder()
                .name("giseo")
                .age(30)
                .hobby("축구")
                .build();

        assertThrows(RuntimeException.class, () -> memberService.join(sameNameDto));
    }

    @Test
    @DisplayName("회원의 Id로 회원 정보를 조회할 수 있다.")
    void 회원_조회_성공() {
        //given
        Long savedMemberId = memberService.join(memberCreateDto);

        //when
        MemberResponseDto memberResponseDto = memberService.findById(savedMemberId);

        //then
        assertThat(memberResponseDto.getAge()).isEqualTo(memberCreateDto.getAge());
        assertThat(memberResponseDto.getName()).isEqualTo(memberCreateDto.getName());
        assertThat(memberResponseDto.getHobby()).isEqualTo(memberCreateDto.getHobby());
    }

    @Test
    @DisplayName("존재하지 않는 Id로 회원 조회 시 예외가 발생한다.")
    void 회원_조회_실패() {
        //givne
        Long unknownId = 1L;

        //when, then
        assertThrows(RuntimeException.class, () -> memberService.findById(unknownId));
    }
}