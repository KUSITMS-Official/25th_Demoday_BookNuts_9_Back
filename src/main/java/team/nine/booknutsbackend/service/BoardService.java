package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.exception.board.BoardNotFoundException;
import team.nine.booknutsbackend.repository.BoardRepository;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //게시글 작성
    @Transactional
    public Board write(Board newBoard) {
        return boardRepository.save(newBoard);
    }

    //게시글 조회
    @Transactional(readOnly = true)
    public Board find(Long boardId) throws BoardNotFoundException {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 아이디입니다."));
    }

    //게시글 수정
    @Transactional
    public Board update(Board board) {
        return boardRepository.save(board);
    }
}