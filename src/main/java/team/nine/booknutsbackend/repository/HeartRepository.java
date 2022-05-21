package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.reaction.Heart;

public interface HeartRepository extends JpaRepository<Heart, Long> {
}
