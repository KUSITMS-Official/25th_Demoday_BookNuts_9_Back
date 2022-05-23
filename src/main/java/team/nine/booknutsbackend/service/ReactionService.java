package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.reaction.Heart;
import team.nine.booknutsbackend.domain.reaction.Nuts;
import team.nine.booknutsbackend.exception.board.BoardNotFoundException;
import team.nine.booknutsbackend.repository.BoardRepository;
import team.nine.booknutsbackend.repository.HeartRepository;
import team.nine.booknutsbackend.repository.NutsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReactionService {

    private final HeartRepository heartRepository;
    private final NutsRepository nutsRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public String clickNuts(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 아이디입니다."));

        List<Nuts> nutsList = user.getNutsList();

        for (Nuts nuts : nutsList) {
            if (nuts.getBoard() == board) {
                nutsRepository.delete(nuts);
                return "넛츠 취소";
            }
        }

        Nuts nuts = new Nuts();
        nuts.setBoard(board);
        nuts.setUser(user);
        nutsRepository.save(nuts);
        return "넛츠 누름";
    }

    @Transactional
    public String clickHeart(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 아이디입니다."));

        List<Heart> hearts = user.getHearts();

        for (Heart heart : hearts) {
            if (heart.getBoard() == board) {
                heartRepository.delete(heart);
                return "좋아요 취소";
            }
        }

        Heart heart = new Heart();
        heart.setBoard(board);
        heart.setUser(user);
        heartRepository.save(heart);
        return "좋아요 누름";
    }
}
