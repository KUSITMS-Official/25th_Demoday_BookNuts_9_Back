package team.nine.booknutsbackend.dto.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    Boolean loginUser;
    Boolean follow;
    UserResponse userResponse;
    int userFollowerCount;
    int userFollowingCount;
}