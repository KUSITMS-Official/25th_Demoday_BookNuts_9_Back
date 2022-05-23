package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;

@Getter
@Builder
public class DebateRoomResponse {

    Long roomId;
    String bookTitle;
    String bookAuthor;
    String bookImgUrl;
    String bookGenre;
    String topic;
    String coverImgUrl;
    int type;
    int maxUser;
    int curYesUser;
    int curNoUser;
    int status;
    String owner;

    public static DebateRoomResponse roomResponse(DebateRoom room) {
        return DebateRoomResponse.builder()
                .roomId(room.getDebateRoomId())
                .bookTitle(room.getBookTitle())
                .bookAuthor(room.getBookAuthor())
                .bookImgUrl(room.getBookImgUrl())
                .bookGenre(room.getBookGenre())
                .topic(room.getTopic())
                .coverImgUrl(room.getCoverImgUrl())
                .type(room.getType())
                .maxUser(room.getMaxUser())
                .curYesUser(room.getCurYesUser())
                .curNoUser(room.getCurNoUser())
                .status(room.getStatus())
                .owner(room.getOwner().getNickname())
                .build();
    }

}
