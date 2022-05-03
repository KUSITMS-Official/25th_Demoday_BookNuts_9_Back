package team.nine.booknutsbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionResponse {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> CustomExceptionHandler(Exception e) {
        return new ResponseEntity<>(responseFormat(e), HttpStatus.BAD_REQUEST);
    }

    private Object responseFormat(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("error", e.toString());
        return map;
    }
}
