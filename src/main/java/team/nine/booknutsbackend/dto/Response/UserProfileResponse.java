package team.nine.booknutsbackend.dto.Response;
import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Follow;
import team.nine.booknutsbackend.domain.User;

import java.util.List;

@Getter
@Builder
public class UserProfileResponse {

    Long userId;
    String nickname;
    Boolean isMyProfile;
    Boolean isFollow;
    int followerCount;
    int followingCount;

    public static UserProfileResponse userProfileResponse(User curUser, User targetUser){
        return UserProfileResponse.builder()
                .userId(targetUser.getUserId())
                .nickname(targetUser.getNickname())
                .isMyProfile(curUser == targetUser)
                .isFollow(getIsFollow(curUser, targetUser))
                .followerCount(targetUser.getFollowings().size())
                .followingCount(targetUser.getFollowers().size())
                .build();

    }

    private static Boolean getIsFollow(User curUser, User targetUser){
        List<Follow> followList = curUser.getFollowers();

        for (Follow follow : followList) {
            if (follow.getFollower() == targetUser) {
                return true;
            }
        }
        return false;
    }
}