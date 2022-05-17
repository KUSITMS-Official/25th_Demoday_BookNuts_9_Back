package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;
import team.nine.booknutsbackend.domain.Debate.DebateUser;

import java.util.List;

public interface DebateUserRepository extends JpaRepository<DebateUser, Long> {
    List<DebateUser> findByDebateRoom(DebateRoom room);
}
