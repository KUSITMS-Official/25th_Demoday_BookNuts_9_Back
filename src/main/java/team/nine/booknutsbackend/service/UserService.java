package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.repository.UserRepository;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    //유저 닉네임 중복 체크
    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    //유저 로그인 아이디 중복 체크
    @Transactional(readOnly = true)
    public boolean checkLoginIdDuplication(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    //회원가입
    @Transactional
    public User join(User newUser) {
        return userRepository.save(newUser);
    }

    //이메일로 유저 정보 조회
    @Transactional(readOnly = true)
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("가입되지 않은 이메일입니다."));
    }

    //ID로 유저 정보 조회
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저의 아이디입니다."));
    }

    //토큰 업데이트
    @Transactional
    public void updateToken(Long id, String token) {
        User user = findUserById(id);
        user.setAccessToken(token);
        userRepository.save(user);
    }

}