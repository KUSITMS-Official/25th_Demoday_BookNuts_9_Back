package team.nine.booknutsbackend.dto.Request;

import lombok.Getter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardRequest {

    @NotBlank String title;
    @NotBlank String content;
    @NotBlank String bookTitle;
    @NotBlank String bookImgUrl;
    @NotBlank String bookGenre;

    public static Board newBoard(BoardRequest boardRequest, User user) {
        Board board = new Board();
        board.setTitle(boardRequest.getTitle());
        board.setContent(boardRequest.getContent());
        board.setBookTitle(boardRequest.getBookTitle());
        board.setBookImgUrl(boardRequest.getBookImgUrl());
        board.setBookGenre(boardRequest.getBookGenre());
        board.setUser(user);

        return board;
    }

}
