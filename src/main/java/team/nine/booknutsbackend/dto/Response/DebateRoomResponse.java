package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Debate.DebateRoom;

import java.time.Duration;
import java.time.LocalDateTime;

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
    String timeFromNow;
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
                .timeFromNow(getTimeFromNow(room))
                .type(room.getType())
                .maxUser(room.getMaxUser())
                .curYesUser(room.getCurYesUser())
                .curNoUser(room.getCurNoUser())
                .status(room.getStatus())
                .owner(room.getOwner().getNickname())
                .build();
    }

    private static String getTimeFromNow(DebateRoom room) {
        LocalDateTime createdAt = room.getCreatedAt();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, now);

        if (duration.toDaysPart() > 0) return duration.toDaysPart() + "일 전";
        if (duration.toHoursPart() > 0) return duration.toHoursPart() + "시간 전";
        if (duration.toMinutesPart() > 0) return duration.toMinutesPart() + "분 전";

        return "방금 전";
    }
}
