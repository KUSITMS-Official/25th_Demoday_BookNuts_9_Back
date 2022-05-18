package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;
import team.nine.booknutsbackend.domain.Debate.DebateUser;
import team.nine.booknutsbackend.domain.User;

import java.util.Optional;

public interface DebateUserRepository extends JpaRepository<DebateUser, Long> {
    DebateUser findByUser(User user);
    int countByDebateRoomAndOpinion(DebateRoom room, Boolean opinion);
    Optional<DebateUser> findByDebateRoomAndUser(DebateRoom room, User user);
}