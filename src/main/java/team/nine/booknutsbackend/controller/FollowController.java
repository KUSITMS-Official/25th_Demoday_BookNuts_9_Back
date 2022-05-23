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
}
