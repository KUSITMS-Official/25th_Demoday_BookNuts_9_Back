package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollowingAndFollower(User unfollowing, User follower);

    //나를 팔로우하는 사람 수 체크
    int countByFollowing(User userId);

    //내가 팔로우하는 사람 수 체크
    int countByFollower(User userId);


    int countByFollowingAndFollower(User Following, User currentUserId);

    //내 팔로잉 리스트
    List<Follow> findByFollower(User userId);

    //내 팔로워 리스트
    List<Follow> findByFollowing(User userId);
}
