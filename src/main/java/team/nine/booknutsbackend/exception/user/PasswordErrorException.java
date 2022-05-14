package team.nine.booknutsbackend.exception.user;

public class PasswordErrorException extends IllegalArgumentException {
    public PasswordErrorException(String msg) {
        super(msg);
    }
}
