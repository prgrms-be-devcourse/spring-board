package kr.co.boardmission.board.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.boardmission.BoardMissionApplication;
import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardFactory;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import kr.co.boardmission.member.application.MemberService;
import kr.co.boardmission.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(classes = BoardMissionApplication.class)
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardRepository boardRepository;

    private BoardCreateRequest request;
    private Member member;
    private Board board;

    @BeforeEach
    void beforeEach() {
        request = BoardFactory.createBoardCreateRequest();
        member = memberService.createMember("testMemberName");
        board = boardRepository.save(BoardFactory.createBoard("title", "content", member));
    }

    @DisplayName("/api/v1/boards - 게시판 등록 API 테스트")
    @Test
    void create_board_api_success() throws Exception {
        // When // Then
        mockMvc.perform(post("/api/v1/boards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }

    @DisplayName("/api/v1/boards/{board_id} - 게시판 단건 조회 API 테스트")
    @Test
    void get_board_api_success() throws Exception {
        
        // When // Then
        mockMvc.perform(get("/api/v1/boards/" + board.getBoardId()))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.createdBy").exists());
    }
}
