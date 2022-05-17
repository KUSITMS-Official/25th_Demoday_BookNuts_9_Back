package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.exception.board.BoardNotFoundException;
import team.nine.booknutsbackend.exception.board.NoAccessException;
import team.nine.booknutsbackend.repository.BoardRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //게시글 작성
    @Transactional
    public Board write(Board newBoard) {
        return boardRepository.save(newBoard);
    }

    //모든 게시글 조회
    @Transactional(readOnly = true)
    public List<BoardResponse> allPosts(User user) {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponse> boardDtoList = new ArrayList<>();

        for (Board board : boards) {
            if (!board.getDeleteCheck()) boardDtoList.add(BoardResponse.boardResponse(board, user));
        }

        Collections.reverse(boardDtoList); //최신순
        return boardDtoList;
    }

    //특정 게시글 조회
    @Transactional(readOnly = true)
    public Board find(Long boardId) throws BoardNotFoundException {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 아이디입니다."));
    }

    //게시글 수정 및 삭제 (데이터 업데이트)
    @Transactional
    public Board update(Board board, Long userId) throws NoAccessException {
        if (board.getUser().getUserId() != userId) {
            throw new NoAccessException("해당 유저는 수정/삭제 권한이 없습니다.");
        }
        return boardRepository.save(board);
    }

}