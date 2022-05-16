package team.nine.booknutsbackend.exception.user;

public class UnAuthTokenException extends Exception {
    public UnAuthTokenException(String msg) {
        super(msg);
    }
}