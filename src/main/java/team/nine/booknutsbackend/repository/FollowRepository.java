package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;



public interface FollowRepository extends JpaRepository<Follow, Long> {
    int countByFollowerAndFollowing(Long userId, String loginId); // 팔로우 되어있는지 count하는 메서드

    Follow findByFollowingAndFollower(User unfollowing, User follower);
}
