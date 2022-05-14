package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.repository.BoardRepository;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //게시글 작성
    @Transactional
    public Board newPost(Board newBoard) {
        return boardRepository.save(newBoard);
    }
}