package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;

public interface ArchiveBoardRepository extends JpaRepository<ArchiveBoard, User> {
}
