package team.nine.booknutsbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.nine.booknutsbackend.config.JwtTokenProvider;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.repository.UserRepository;
import team.nine.booknutsbackend.service.UserService;

import java.util.Collections;
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

        return new ResponseEntity<Object>(newUser, HttpStatus.OK);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지않은 이메일입니다."));

        if (!passwordEncoder.matches(user.get("password"), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(member.getUsername(), member.getRoles()); //getUsername -> 이메일 반환
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    // 현재 유저 정보
    @PostMapping("/userinfo")
    public ResponseEntity<Object> curUser(@RequestBody Map<String, String> userToken) {
        String email = jwtTokenProvider.getUserPk(userToken.get("token"));
        UserDetails loginUser = userService.loadUserByUsername(email);

        return new ResponseEntity<Object>(loginUser, HttpStatus.OK);
    }
}