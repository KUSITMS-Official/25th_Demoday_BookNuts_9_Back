package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Discussion;

@Getter
@Builder
public class DiscussionListResponse {

    Long roomId;
    String coverImgUrl;
    String topic;
    String bookTitle;
    int type;
    int curYesUser;
    int curNoUser;
    String owner;

    public static DiscussionListResponse listResponse(Discussion room) {
        return DiscussionListResponse.builder()
                .roomId(room.getDiscussionId())
                .coverImgUrl(room.getCoverImgUrl())
                .topic(room.getTopic())
                .bookTitle(room.getBookTitle())
                .type(room.getType())
                .curYesUser(room.getCurYesUser())
                .curNoUser(room.getCurNoUser())
                .owner(room.getUser().getNickname())
                .build();
    }
}
