package team.nine.booknutsbackend.dto.Request;

import lombok.Getter;
import team.nine.booknutsbackend.domain.User;

import javax.validation.constraints.NotBlank;
import java.util.Collections;

@Getter
public class UserRequest {

    @NotBlank String loginId;
    @NotBlank String password;
    @NotBlank String username;
    @NotBlank String nickname;
    @NotBlank String email;
    String roles;

    public static User newUser(UserRequest userRequest, String encodePW) {
        User user = new User();
        user.setLoginId(userRequest.getLoginId());
        user.setPassword(encodePW);
        user.setUsername(userRequest.getUsername());
        user.setNickname(userRequest.getNickname());
        user.setEmail(userRequest.getEmail());
        user.setRoles(Collections.singletonList("ROLE_USER"));

        return user;
    }

}
