package team.nine.booknutsbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.nine.booknutsbackend.exception.user.NotFoundEmailException;
import team.nine.booknutsbackend.exception.user.PasswordErrorException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * Common Exception Handler
     **/
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> CommonExceptionHandler(Exception e) {
        return new ResponseEntity<>(responseFormat(e), HttpStatus.BAD_REQUEST);
    }

    /**
     * 로그인 Exception
     **/
    // 가입되지 않은 이메일
    @ExceptionHandler(NotFoundEmailException.class)
    public ResponseEntity<Object> notFoundEmailHandler(NotFoundEmailException e) {
        return new ResponseEntity<>(responseFormat(e), HttpStatus.NOT_FOUND);
    }

    //잘못된 비밀번호
    @ExceptionHandler(PasswordErrorException.class)
    public ResponseEntity<Object> notFoundEmailHandler(PasswordErrorException e) {
        return new ResponseEntity<>(responseFormat(e), HttpStatus.BAD_REQUEST);
    }

    private Object responseFormat(Exception e) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("error", e.getClass().getName());
        map.put("msg", e.getMessage());
        return map;
    }
}
