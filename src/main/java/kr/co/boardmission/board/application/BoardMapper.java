package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public Board toBoard(BoardCreateRequest request) {
        return Board.create(request.getTitle(), request.getContent(), request.getCreatedBy());
    }
}
