package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.repository.FollowRepository;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    //팔로우
    public void save(User following, User follower) {
        Follow follow = new Follow();

        follow.setFollowing(userService.findUserById(following.getUserId()));
        follow.setFollower(userService.findUserById(follower.getUserId()));

        followRepository.save(follow);
    }

    //언팔로우
    public void deleteByFollowingIdAndFollowerId(User unfollowing, User follower) {
        Follow follow = followRepository.findByFollowingAndFollower(unfollowing, follower);
        followRepository.delete(follow);
    }

    //controller로 구현 안함. 필요할지 의논
    //팔로우 여부 확인
    public boolean find(Long id, String loginId) {
        if(followRepository.countByFollowerAndFollowing(id, loginId) == 0)
            return false; // 팔로우 안되어있음
        return true; // 되어있음
    }

}
