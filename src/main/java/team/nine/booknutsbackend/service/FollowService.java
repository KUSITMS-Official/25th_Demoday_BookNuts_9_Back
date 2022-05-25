package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Response.UserResponse;
import team.nine.booknutsbackend.exception.archive.ArchiveNotFoundException;
import team.nine.booknutsbackend.exception.follow.FollowDuplicateException;
import team.nine.booknutsbackend.repository.FollowRepository;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    //팔로우
    public void save(User following, User follower) {
        Follow follow = new Follow();

        if(followRepository.findByFollowingAndFollower(following, follower).isPresent())
            throw new FollowDuplicateException("이미 팔로잉한 계정입니다");

        follow.setFollowing(userService.findUserById(following.getUserId()));
        follow.setFollower(userService.findUserById(follower.getUserId()));

        followRepository.save(follow);
    }

    //언팔로우
    public void deleteByFollowingIdAndFollowerId(User unfollowing, User follower) {
        Follow follow = followRepository.findByFollowingAndFollower(unfollowing, follower)
                .orElseThrow(() -> new FollowDuplicateException("팔로잉하지 않은 계정입니다."));
        followRepository.delete(follow);
    }

    //controller로 구현 안함. 필요할지 의논
    //팔로우 여부 확인
//    public boolean find(Long id, String loginId) {
//        if(followRepository.countByFollowerAndFollowing(id, loginId) == 0)
//            return false; // 팔로우 안되어있음
//        return true; // 되어있음
//    }

    //나를 팔로우 하는 유저 수 체크
    public int findFollowing(User currentUserId) {
        return followRepository.countByFollowing(currentUserId);
    }

    //내가 팔로우 하는 유저 수 체크
    public int findFollower(User currentUserId) {
        return followRepository.countByFollower(currentUserId);
    }

    //내가 팔로우한 사용자인지 체크
    public boolean checkFollowingMe(User following, User currentUserId){
        if(followRepository.countByFollowingAndFollower(following, currentUserId) == 0)
            return false;
        return true;
    }

    //나의 팔로잉 리스트
    public List<UserResponse> findMyFollowingList(User userId) {
        List<Follow> followlist= followRepository.findByFollower(userId);
        List<UserResponse> followingUserList = new ArrayList<>();

        for (Follow follow : followlist) {
            followingUserList.add(UserResponse.newUserResponse(follow.getFollowing()));
        }

        return  followingUserList;
    }

    //나의 팔로워 리스트
    public List<UserResponse> findMyFollowerList(User userId) {
        List<Follow> followlist= followRepository.findByFollowing(userId);
        List<UserResponse> followerUserList = new ArrayList<>();

        for (Follow follow : followlist) {
            followerUserList.add(UserResponse.newUserResponse(follow.getFollower()));
        }

        return  followerUserList;
    }
}
