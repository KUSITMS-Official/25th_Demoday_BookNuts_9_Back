package team.nine.booknutsbackend.exception.user;

public class NicknameDuplicateException extends IllegalStateException{
    public NicknameDuplicateException(String msg) {
        super(msg);
    }
}
