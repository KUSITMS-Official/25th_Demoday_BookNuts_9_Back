package team.nine.booknutsbackend.exception.archive;

public class ArchiveNotFoundException extends IllegalArgumentException {
    public ArchiveNotFoundException(String msg) {
        super(msg);
    }
}