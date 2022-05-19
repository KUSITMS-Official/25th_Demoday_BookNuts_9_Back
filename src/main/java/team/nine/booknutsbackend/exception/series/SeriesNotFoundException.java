package team.nine.booknutsbackend.exception.series;

public class SeriesNotFoundException extends IllegalArgumentException {
    public SeriesNotFoundException(String msg) {
        super(msg);
    }
}
