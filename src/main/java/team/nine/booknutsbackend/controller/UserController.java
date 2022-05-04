package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import team.nine.booknutsbackend.config.JwtTokenProvider;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.exception.user.NotFoundEmailException;
import team.nine.booknutsbackend.exception.user.PasswordErrorException;
import team.nine.booknutsbackend.repository.UserRepository;
import team.nine.booknutsbackend.service.UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<Object> join(@RequestBody Map<String, String> user) {

        User newUser = userRepository.save(User.builder()
                .userId(user.get("userId"))
                .password(passwordEncoder.encode(user.get("password")))
                .username(user.get("username"))
                .nickname(user.get("nickname"))
                .email(user.get("email"))
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    //유저 아이디 중복 체크
    @GetMapping("/checkNickname/{nickname}")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname){
        return ResponseEntity.ok(userService.checkNicknameDuplication(nickname));
    }

    //유저 닉네임 중복 체크
    @GetMapping("/checkUserId/{userid}")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userid){
        return ResponseEntity.ok(userService.checkUserIdDuplication(userid));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new NotFoundEmailException("가입되지않은 이메일입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new PasswordErrorException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles()); //getUsername -> 이메일 반환

        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // 현재 유저 정보
    @PostMapping("/userinfo")
    public ResponseEntity<Object> curUser(@RequestBody Map<String, String> userToken) {
        String email = jwtTokenProvider.getUserPk(userToken.get("token"));
        UserDetails loginUser = userService.loadUserByUsername(email);

        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }
}