package team.nine.booknutsbackend.exception.Debate;

public class RoomNotFoundException extends IllegalArgumentException {
    public RoomNotFoundException(String msg) {
        super(msg);
    }
}
