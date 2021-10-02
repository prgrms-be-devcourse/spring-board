package com.programmers.springbootboard.member.presentation;

import com.programmers.springbootboard.common.dto.ResponseDto;
import com.programmers.springbootboard.common.dto.ResponseMessage;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.dto.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<ResponseDto> insertMember(@RequestBody MemberSignRequest request) {
        memberService.insert(request);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.SIGN_SUCCESS));
    }

    @DeleteMapping("/member")
    public ResponseEntity<ResponseDto> deleteMember(@RequestBody Long id) {
        memberService.delete(id);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.DELETE_SUCCESS));
    }

    @PutMapping("/member")
    public ResponseEntity<ResponseDto> updateMember(@RequestBody Long id, @RequestBody MemberUpdateRequest request) {
        memberService.update(id, request);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.UPDATE_SUCCESS));
    }

    @GetMapping("/member")
    public ResponseEntity<ResponseDto> member(@RequestBody Long id) {
        MemberDetailResponse member = memberService.member(id);
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.INQUIRY_MEMBER_SUCCESS, member));
    }

    @GetMapping("/members")
    public ResponseEntity<ResponseDto> members() {
        List<MemberDetailResponse> members = memberService.members();
        return ResponseEntity.ok(ResponseDto.of(ResponseMessage.INQUIRY_MEMBER_SUCCESS, members));
    }

}
