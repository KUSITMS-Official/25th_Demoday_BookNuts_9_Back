package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.exception.user.NicknameDuplicateException;
import team.nine.booknutsbackend.exception.user.UserIdDuplicateException;
import team.nine.booknutsbackend.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    // 유저 정보 조회
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    //유저 닉네임 중복 체크
    @Transactional(readOnly = true)
    public void checkNicknameDuplication(String nickname) {
        boolean nicknameDuplicate = userRepository.existsByNickname(nickname);
        if (nicknameDuplicate) {
            throw new NicknameDuplicateException("이미 존재하는 닉네임입니다.");
        }
    }

    //유저 아이디 중복 체크
    @Transactional(readOnly = true)
    public void checkUserIdDuplication(String userId) {
        boolean userIdDuplicate = userRepository.existsByUserId(userId);
        if (userIdDuplicate) {
            throw new UserIdDuplicateException("이미 존재하는 아이디입니다.");
        }
    }

}