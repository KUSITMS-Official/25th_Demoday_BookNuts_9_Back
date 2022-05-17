package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Debate;

@Getter
@Builder
public class DebateListResponse {

    Long roomId;
    String coverImgUrl;
    String topic;
    String bookTitle;
    int type;
    int curYesUser;
    int curNoUser;
    String owner;

    public static DebateListResponse listResponse(Debate room) {
        return DebateListResponse.builder()
                .roomId(room.getDebateId())
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
