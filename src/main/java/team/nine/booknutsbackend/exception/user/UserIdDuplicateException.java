package team.nine.booknutsbackend.exception.user;

public class UserIdDuplicateException extends IllegalStateException{
    public UserIdDuplicateException(String msg) {
        super(msg);
    }
}
