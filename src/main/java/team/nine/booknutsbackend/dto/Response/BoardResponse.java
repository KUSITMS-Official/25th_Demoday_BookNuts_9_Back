package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;
import team.nine.booknutsbackend.domain.reaction.Heart;
import team.nine.booknutsbackend.domain.reaction.Nuts;

import java.util.List;
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
    int nutsCnt;
    int heartCnt;
    int archiveCnt;
    Boolean isNuts;
    Boolean isHeart;
    Boolean isArchived;
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
                .nutsCnt(board.getNutsCnt())
                .heartCnt(board.getHeartCnt())
                .archiveCnt(board.getArchiveCnt())
                .isNuts(getIsNuts(board, user))
                .isHeart(getIsHeart(board, user))
                .isArchived(getIsArchived(board, user))
                .curUser(Objects.equals(board.getUser().getUserId(), user.getUserId()))
                .build();
    }

    private static Boolean getIsNuts(Board board, User user) {
        List<Nuts> nutsList = user.getNutsList();
        for (Nuts nuts : nutsList) {
            if (nuts.getBoard().equals(board)) return true;
        }
        return false;
    }

    private static Boolean getIsHeart(Board board, User user) {
        List<Heart> hearts = user.getHearts();
        for (Heart heart : hearts) {
            if (heart.getBoard().equals(board)) return true;
        }
        return false;
    }

    private static Boolean getIsArchived(Board board, User user) {
        List<ArchiveBoard> archiveBoards = user.getArchiveBoards();
        for (ArchiveBoard archiveBoard : archiveBoards) {
            if (archiveBoard.getBoard().equals(board)) return true;
        }
        return false;
    }
}
