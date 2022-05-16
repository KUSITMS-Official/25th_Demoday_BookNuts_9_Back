package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Discussion;

public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
}
