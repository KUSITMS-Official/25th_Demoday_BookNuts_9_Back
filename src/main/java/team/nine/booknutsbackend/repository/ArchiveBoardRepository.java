package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;

import java.util.List;

public interface ArchiveBoardRepository extends JpaRepository<ArchiveBoard, User> {
    List<ArchiveBoard> findByArchive(Archive archive);
    int countByBoard(Board board);
    List<ArchiveBoard> findByBoard(Board board);
    ArchiveBoard findByArchiveAndBoard(Archive archive, Board board);
}
