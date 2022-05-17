package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Debate;

@Getter
@Builder
public class RoomResponse {

    Long roomId;
    String bookTitle;
    String bookImgUrl;
    String bookGenre;
    String topic;
    String coverImgUrl;
    int type;
    int curYesUser;
    int curNoUser;
    int status;
    String owner;

    public static RoomResponse roomResponse(Debate room) {
        return RoomResponse.builder()
                .roomId(room.getDebateId())
                .bookTitle(room.getBookTitle())
                .bookImgUrl(room.getBookImgUrl())
                .bookGenre(room.getBookGenre())
                .topic(room.getTopic())
                .coverImgUrl(room.getCoverImgUrl())
                .type(room.getType())
                .curYesUser(room.getCurYesUser())
                .curNoUser(room.getCurNoUser())
                .status(room.getStatus())
                .owner(room.getUser().getNickname())
                .build();
    }
}
