package team.nine.booknutsbackend.dto.Request;

import lombok.Getter;
import team.nine.booknutsbackend.domain.Debate;
import team.nine.booknutsbackend.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class DebateRoomRequest {

    @NotBlank String bookTitle;
    @NotBlank String bookImgUrl;
    @NotBlank String bookGenre;
    @NotBlank String topic;
    @NotBlank String coverImgUrl;
    @NotNull int type;
    @NotNull int maxUser;
    @NotNull int yesno;
    int status;
    int curYesUser;
    int curNoUser;

    public static Debate newRoom(DebateRoomRequest roomRequest, User user) {
        Debate room = new Debate();
        room.setBookTitle(roomRequest.getBookTitle());
        room.setBookImgUrl(roomRequest.getBookImgUrl());
        room.setBookGenre(roomRequest.getBookGenre());
        room.setTopic(roomRequest.getTopic());
        room.setCoverImgUrl(roomRequest.getCoverImgUrl());
        room.setType(roomRequest.getType());
        room.setMaxUser(roomRequest.getMaxUser());
        room.setUser(user);

        //개설자가 '반대'일 경우
        if (roomRequest.getYesno() == 0) {
            room.setCurYesUser(0);
            room.setCurNoUser(1);
        }

        //개설자가 '찬성'일 경우
        else {
            room.setCurYesUser(1);
            room.setCurNoUser(0);
        }

        return room;
    }
}
