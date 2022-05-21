package team.nine.booknutsbackend.exception.archive;

public class ArchiveDuplicateException extends IllegalArgumentException {
    public ArchiveDuplicateException(String msg) {
        super(msg);
    }
}
