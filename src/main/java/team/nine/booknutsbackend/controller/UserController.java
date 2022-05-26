package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Response.UserProfileResponse;
import team.nine.booknutsbackend.service.AuthService;
import team.nine.booknutsbackend.service.FollowService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final FollowService followService;
    private final AuthService userService;

    //사용자 프로필 조회
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable Long userId, Principal principal) {
        User curUser = userService.loadUserByUsername(principal.getName());
        User targetUser = userService.findUserById(userId);
        return new ResponseEntity<>(UserProfileResponse.userProfileResponse(curUser, targetUser), HttpStatus.OK);
    }

}
