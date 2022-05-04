package team.nine.booknutsbackend.exception.user;

public class NotFoundEmailException extends IllegalArgumentException {
    public NotFoundEmailException() {
        super();
    }

    public NotFoundEmailException(String msg) {
        super(msg);
    }
}
