package team.nine.booknutsbackend.dto;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
public class BoardDto {

    Long boardId;
    String title;
    String content;
    String writer;
    LocalDateTime createdDate;
    String bookTitle;
    String bookImgUrl;
    String bookGenre;
    Boolean curUser;

    public static BoardDto boardResponse(Board board, User user) {
        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getUser().getNickname())
                .createdDate(board.getCreatedDate())
                .bookTitle(board.getBookTitle())
                .bookImgUrl(board.getBookImgUrl())
                .bookGenre(board.getBookGenre())
                .curUser(Objects.equals(board.getUser().getUserId(), user.getUserId()))
                .build();
    }
}
