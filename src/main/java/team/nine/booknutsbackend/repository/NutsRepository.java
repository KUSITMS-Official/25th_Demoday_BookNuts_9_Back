package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.reaction.Nuts;

public interface NutsRepository extends JpaRepository<Nuts, Long> {
    int countByBoard(Board board);
    Nuts findByBoardAndUser(Board board, User user);
}
