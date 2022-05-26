package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingAndFollower(User unfollowing, User follower);
    int countByFollowingAndFollower(User Following, User currentUserId);
    int countByFollower(User curUser); //나의 팔로잉(내가 팔로우 하는)
    int countByFollowing(User curUser); //나의 팔로워(나를 팔로우 하는)
    List<Follow> findByFollower(User curUser); //내 팔로잉 리스트
    List<Follow> findByFollowing(User curUser); //내 팔로워 리스트
}
