package team.nine.booknutsbackend.exception.Debate;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
