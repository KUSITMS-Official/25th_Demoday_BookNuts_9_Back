package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.exception.board.BoardNotFoundException;
import team.nine.booknutsbackend.exception.board.NoAccessException;
import team.nine.booknutsbackend.repository.ArchiveBoardRepository;
import team.nine.booknutsbackend.repository.BoardRepository;
import team.nine.booknutsbackend.repository.HeartRepository;
import team.nine.booknutsbackend.repository.NutsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ArchiveBoardRepository archiveBoardRepository;
    private final NutsRepository nutsRepository;
    private final HeartRepository heartRepository;

    //게시글 작성
    @Transactional
    public Board write(Board newBoard) {
        return boardRepository.save(newBoard);
    }

    //게시글 조회
    //*** 타입에 따른 반환 구현 전 ***
    @Transactional(readOnly = true)
    public List<BoardResponse> boardList(User user, int type) {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponse> boardDtoList = new ArrayList<>();

        for (Board board : boards) {
            boardDtoList.add(BoardResponse.boardResponse(board, user));
        }

        Collections.reverse(boardDtoList); //최신순
        return boardDtoList;
    }

    //특정 게시글 조회
    @Transactional(readOnly = true)
    public Board findBoard(Long boardId) throws BoardNotFoundException {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 아이디입니다."));
    }

    //게시글 수정
    @Transactional
    public Board update(Board board, User user) throws NoAccessException {
        boardRepository.findByBoardIdAndUser(board.getBoardId(), user)
                .orElseThrow(() -> new NoAccessException("해당 유저는 수정 권한이 없습니다."));

        return boardRepository.save(board);
    }

    //게시글 삭제
    @Transactional
    public void delete(Long boardId, User user) throws NoAccessException {
        Board board = boardRepository.findByBoardIdAndUser(boardId, user)
                .orElseThrow(() -> new NoAccessException("해당 유저는 삭제 권한이 없습니다."));

        boardRepository.delete(board);
    }

}