package team.nine.booknutsbackend.exception.Discussion;

public class RoomNotFoundException extends IllegalArgumentException {
    public RoomNotFoundException(String msg) {
        super(msg);
    }
}
