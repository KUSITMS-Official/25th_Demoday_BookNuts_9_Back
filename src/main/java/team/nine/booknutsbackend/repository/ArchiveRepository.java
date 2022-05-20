package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;

import java.util.List;
import java.util.Optional;

public interface ArchiveRepository extends JpaRepository<Archive, Long> {
    List<Archive> findAllByUser(User user);

    Optional<Archive> findByArchiveIdAndUser(Long archiveId, User user);

}
