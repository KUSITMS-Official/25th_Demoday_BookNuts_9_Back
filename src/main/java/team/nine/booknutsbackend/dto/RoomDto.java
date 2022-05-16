package team.nine.booknutsbackend.dto;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Discussion;
import team.nine.booknutsbackend.domain.User;

@Getter
@Builder
public class RoomDto {

    Long roomId;
    String bookTitle;
    String bookImgUrl;
    String bookGenre;
    String topic;
    String coverImgUrl;
    int type;
    Long curYesUser;
    Long curNoUser;
    int status;
    String owner;

    public static RoomDto roomResponse(Discussion room, User user) {
        return RoomDto.builder()
                .roomId(room.getDiscussionId())
                .bookTitle(room.getBookTitle())
                .bookImgUrl(room.getBookImgUrl())
                .bookGenre(room.getBookGenre())
                .topic(room.getTopic())
                .coverImgUrl(room.getCoverImgUrl())
                .type(room.getType())
                .curYesUser(room.getCurYesUser())
                .curNoUser(room.getCurNoUser())
                .status(room.getStatus())
                .owner(user.getNickname())
                .build();
    }

}
