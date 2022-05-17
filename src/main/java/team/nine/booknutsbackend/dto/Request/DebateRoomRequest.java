package team.nine.booknutsbackend.dto.Request;

import lombok.Getter;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;
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
    @NotNull boolean opinion;
    User owner;

    public static DebateRoom newRoom(DebateRoomRequest roomRequest, User user) {
        DebateRoom room = new DebateRoom();
        room.setBookTitle(roomRequest.getBookTitle());
        room.setBookImgUrl(roomRequest.getBookImgUrl());
        room.setBookGenre(roomRequest.getBookGenre());
        room.setTopic(roomRequest.getTopic());
        room.setCoverImgUrl(roomRequest.getCoverImgUrl());
        room.setType(roomRequest.getType());
        room.setMaxUser(roomRequest.getMaxUser());
        room.setOwner(user);

        return room;
    }
}
