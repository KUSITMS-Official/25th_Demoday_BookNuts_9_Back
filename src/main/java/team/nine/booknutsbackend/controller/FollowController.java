package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.service.FollowService;
import team.nine.booknutsbackend.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    //팔로우
    @GetMapping("/following/{followingId}")
    public ResponseEntity<Object> follow(@PathVariable Long followingId, Principal principal) {
        User follower = userService.loadUserByUsername(principal.getName());
        User following = userService.findUserById(followingId);

        followService.save(following, follower);

        Map<String, String> map = new HashMap<>();
        map.put("result", "팔로잉 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //언팔로우
    @GetMapping("/unfollow/{unfollowingId}")
    public ResponseEntity<Object> unfollow(@PathVariable Long unfollowingId, Principal principal) {
        User follower = userService.loadUserByUsername(principal.getName());    //나(팔로워)
        User unfollowing = userService.findUserById(unfollowingId);

        followService.deleteByFollowingIdAndFollowerId(unfollowing, follower);

        Map<String, String> map = new HashMap<>();
        map.put("result", "언팔로우 완료");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //언팔로우
    @GetMapping("/userprofile/{userId}")
    public ResponseEntity<Object> checkUserProfile(@PathVariable Long userId, Principal principal) {
        User LoginUser = userService.loadUserByUsername(principal.getName());    //나
        Long currentUserId = LoginUser.getUserId();
        User findUser = userService.findUserById(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("nickname", LoginUser.getNickname());
        map.put("followingCount", followService.findFollowing(LoginUser));
        map.put("followerCount", followService.findFollower(LoginUser));

        if ( currentUserId != userId) { //로그인한 아이디와 현재 보는 프로필 아이디가 다른 사용자
            map.put("checkmyprofile", false);
            map.put("checkmyFollowing", followService.checkFollowingMe(findUser, LoginUser));
        } else {
            map.put("checkmyprofile", true);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    
}
