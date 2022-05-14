package team.nine.booknutsbackend.exception.board;

import javax.security.sasl.AuthenticationException;

public class NoAccessException extends AuthenticationException {
    public NoAccessException(String msg) {
        super(msg);
    }
}
