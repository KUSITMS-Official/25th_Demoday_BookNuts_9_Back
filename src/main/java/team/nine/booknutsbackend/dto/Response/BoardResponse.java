package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import java.util.Objects;

@Getter
@Builder
public class BoardResponse {

    Long boardId;
    String title;
    String content;
    String writer;
    String createdDate;
    String bookTitle;
    String bookAuthor;
    String bookImgUrl;
    String bookGenre;
    Boolean curUser;

    public static BoardResponse boardResponse(Board board, User user) {
        return BoardResponse.builder()
                .boardId(board.getBoardId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getUser().getNickname())
                .createdDate(board.getCreatedDate())
                .bookTitle(board.getBookTitle())
                .bookAuthor(board.getBookAuthor())
                .bookImgUrl(board.getBookImgUrl())
                .bookGenre(board.getBookGenre())
                .curUser(Objects.equals(board.getUser().getUserId(), user.getUserId()))
                .build();
    }
}
