package team.nine.booknutsbackend.exception.mystory;

public class SeriesNotFoundException extends IllegalArgumentException {
    public SeriesNotFoundException(String msg) {
        super(msg);
    }
}
