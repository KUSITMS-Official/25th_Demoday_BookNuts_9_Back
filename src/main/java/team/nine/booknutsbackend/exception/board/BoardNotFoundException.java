package team.nine.booknutsbackend.exception.board;

public class BoardNotFoundException extends IllegalArgumentException {
    public BoardNotFoundException(String msg) {
        super(msg);
    }
}
