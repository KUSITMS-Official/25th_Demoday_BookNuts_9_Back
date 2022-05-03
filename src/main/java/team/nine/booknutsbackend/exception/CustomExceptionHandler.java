package team.nine.booknutsbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.nine.booknutsbackend.exception.user.NotFoundEmailException;
import team.nine.booknutsbackend.exception.user.PasswordErrorException;


@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * Common 예외 처리
     **/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> CommonExceptionHandler(Exception e) {
        return new ResponseEntity<>(ExceptionResponse.format(e), HttpStatusMap.getCode(e));
    }

    /**
     * User 관련 예외 처리
     **/
    // 가입되지 않은 이메일
    @ExceptionHandler(NotFoundEmailException.class)
    public ResponseEntity<Object> notFoundEmailHandler(NotFoundEmailException e) {
        return new ResponseEntity<>(ExceptionResponse.format(e), HttpStatus.NOT_FOUND);
    }

    //잘못된 비밀번호
    @ExceptionHandler(PasswordErrorException.class)
    public ResponseEntity<Object> passwordErrorHandler(PasswordErrorException e) {
        return new ResponseEntity<>(ExceptionResponse.format(e), HttpStatus.BAD_REQUEST);
    }
}
