package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;

import java.util.List;
import java.util.Optional;

public interface ArchiveBoardRepository extends JpaRepository<ArchiveBoard, User> {
    List<ArchiveBoard> findByArchive(Archive archive);
    ArchiveBoard findByArchiveAndBoard(Archive archive, Board board);
    Optional<ArchiveBoard> findByBoardAndOwner(Board board, User owner);
}
