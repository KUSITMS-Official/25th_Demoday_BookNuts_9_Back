package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;

@Getter
@Builder
public class UserResponse {

    Long userId;
    String loginId;
    String username;
    String nickname;
    String email;

    public static UserResponse newUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

}
