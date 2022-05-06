package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    //유저 닉네임 중복 체크
    boolean existsByNickname(String nickname);

    //유저 로그인 아이디 중복 체크
    boolean existsByLoginId(String loginId);
}