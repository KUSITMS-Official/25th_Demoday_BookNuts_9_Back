package team.nine.booknutsbackend.exception.follow;

public class FollowNotFoundException extends IllegalArgumentException {
    public FollowNotFoundException(String msg) {
        super(msg);
    }
}