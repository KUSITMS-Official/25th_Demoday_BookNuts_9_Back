package team.nine.booknutsbackend.exception.series;

public class SeriesDuplicateException extends IllegalArgumentException {
    public SeriesDuplicateException(String msg) {
        super(msg);
    }
}