package team.nine.booknutsbackend.exception.mystory;

public class MyStoryNotFoundException extends IllegalArgumentException {
    public MyStoryNotFoundException(String msg) {
        super(msg);
    }
}
