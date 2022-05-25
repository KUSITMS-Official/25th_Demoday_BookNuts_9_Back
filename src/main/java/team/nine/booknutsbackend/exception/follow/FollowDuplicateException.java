package team.nine.booknutsbackend.exception.follow;

public class FollowDuplicateException extends IllegalArgumentException {
    public FollowDuplicateException(String msg) {
        super(msg);
    }
}