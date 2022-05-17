package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Debate;

import java.util.List;

public interface DebateRepository extends JpaRepository<Debate, Long> {
    List<Debate> findByType(int type);
}