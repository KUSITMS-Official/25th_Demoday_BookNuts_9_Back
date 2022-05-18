package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long>  {
    Optional<Board> findByBoardIdAndUser(Long boardId, User user);
}