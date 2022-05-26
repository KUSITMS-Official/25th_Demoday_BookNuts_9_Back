package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Response.UserResponse;
import team.nine.booknutsbackend.exception.follow.FollowDuplicateException;
import team.nine.booknutsbackend.repository.FollowRepository;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final AuthService userService;

    //팔로우
    @Transactional
    public void save(User following, User follower) {
        Follow follow = new Follow();

        if (followRepository.findByFollowingAndFollower(following, follower).isPresent())
            throw new FollowDuplicateException("이미 팔로잉한 계정입니다");

        follow.setFollowing(userService.findUserById(following.getUserId()));
        follow.setFollower(userService.findUserById(follower.getUserId()));

        followRepository.save(follow);
    }

    //언팔로우
    @Transactional
    public void deleteByFollowingIdAndFollowerId(User unfollowing, User follower) {
        Follow follow = followRepository.findByFollowingAndFollower(unfollowing, follower)
                .orElseThrow(() -> new FollowDuplicateException("팔로우하지 않은 계정입니다."));
        followRepository.delete(follow);
    }

    //나의 팔로잉 리스트
    @Transactional(readOnly = true)
    public List<UserResponse> findMyFollowingList(User user) {
        List<Follow> followlist = followRepository.findByFollower(user);
        List<UserResponse> followingUserList = new ArrayList<>();

        for (Follow follow : followlist) {
            followingUserList.add(UserResponse.newUserResponse(follow.getFollowing()));
        }

        return followingUserList;
    }

    //나의 팔로워 리스트
    @Transactional(readOnly = true)
    public List<UserResponse> findMyFollowerList(User user) {
        List<Follow> followlist = followRepository.findByFollowing(user);
        List<UserResponse> followerUserList = new ArrayList<>();

        for (Follow follow : followlist) {
            followerUserList.add(UserResponse.newUserResponse(follow.getFollower()));
        }

        return followerUserList;

    }

}
