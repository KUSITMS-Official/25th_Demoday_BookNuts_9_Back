package team.nine.booknutsbackend.dto;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Board;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardDto {

    Long boardId;
    String title;
    String content;
    String writer;
    LocalDateTime createdDate;
    Boolean deleteCheck;
    String bookTitle;
    String bookImgUrl;
    String bookGenre;

    public static BoardDto boardResponse(Board board) {
        return BoardDto.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getUser().getNickname())
                .createdDate(board.getCreatedDate())
                .deleteCheck(board.getDeleteCheck())
                .bookTitle(board.getBookTitle())
                .bookImgUrl(board.getBookImgUrl())
                .bookGenre(board.getBookGenre())
                .build();
    }
}
