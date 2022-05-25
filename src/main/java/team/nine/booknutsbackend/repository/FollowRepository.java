package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;



public interface FollowRepository extends JpaRepository<Follow, Long> {
    int countByFollowerAndFollowing(Long userId, String loginId); // 팔로우 되어있는지 count하는 메서드

    Follow findByFollowingAndFollower(User unfollowing, User follower);

    int countByFollowing(User userId);   //나를 팔로우하는 사람 수 체크
    int countByFollower(User userId);  //내가 팔로우하는 사람 수 체크
    int countByFollowingAndFollower(User Following, User currentUserId);
}
